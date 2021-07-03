package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.WebApplicationException;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import entity.Arquivo;
import http.ArquivoHttp;
import repository.ArquivoRepository;

@Path("/arquivo")
public class ArquivoController {

	private final ArquivoRepository repository = new ArquivoRepository();

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetails) throws IOException {

		Arquivo arquivo = new Arquivo();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		while ((nRead = uploadedInputStream.read(data, 0, data.length)) != -1) {
			buffer.write(data, 0, nRead);
		}

		try {

			arquivo.setNome(fileDetails.getFileName());
			arquivo.setArquivo(buffer.toByteArray());

			repository.Salvar(arquivo);

			return "Registro cadastrado com sucesso!";

		} catch (Exception e) {

			return "Erro ao cadastrar um registro " + e.getMessage();

		}

	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<ArquivoHttp> selecionarTodos() {

		List<ArquivoHttp> arquivoHttp = new ArrayList<ArquivoHttp>();

		List<Arquivo> arquivos = repository.selecionarTodos();

		for (Arquivo arquivo : arquivos) {

			arquivoHttp.add(new ArquivoHttp(arquivo.getId(), arquivo.getNome(), arquivo.getDataCriacao(), arquivo.getDataAtualizacao()));
		}

		return arquivoHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("/download/{id}")
	public Response selecionarArquivoPorId(@PathParam("id") Long id) {

		Arquivo arquivo = repository.selecionarPorId(id);
		String fileName = "attachment; filename = " + arquivo.getNome();
		StreamingOutput fileStream = new StreamingOutput() {
			@Override
			public void write(java.io.OutputStream output) throws IOException, WebApplicationException {
				try {
					byte[] data = arquivo.getArquivo();
					output.write(data);
					output.flush();
				} catch (Exception e) {
					throw new WebApplicationException("File Not Found !!");
				}
			}
		};
		return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM).header("content-disposition", fileName)
				.build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public String Excluir(@PathParam("id") Long id) {

		try {

			repository.Excluir(id);

			return "Registro excluido com sucesso!";

		} catch (Exception e) {

			return "Erro ao excluir o registro! " + e.getMessage();
		}

	}

}
