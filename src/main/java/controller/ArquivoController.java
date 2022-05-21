package controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
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
	public Response Cadastrar(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetails) throws IOException {
		
		String message = "";
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

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Arquivo>> violations = validator.validate(arquivo);
			for (ConstraintViolation<Arquivo> violation : violations) {
			    message += violation.getMessage() + "\n"; 
			}
			if(message != "") {
				return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
			}
			
			repository.Salvar(arquivo);
			return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso" ).build();			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o registro: " + e.getMessage() ).build();
		}	
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<ArquivoHttp> selecionarTodos() {

		List<ArquivoHttp> arquivoHttp = new ArrayList<ArquivoHttp>();
		List<Arquivo> arquivos = repository.selecionarTodos();
		for (Arquivo arquivo : arquivos) {
			arquivoHttp.add(new ArquivoHttp(arquivo.getId(), arquivo.getNome()));
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
		return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM).header("content-disposition", fileName).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response Excluir(@PathParam("id") Long id) {

		try {
			Arquivo arquivo = repository.selecionarPorId(id);
			if(arquivo != null) {
				repository.Excluir(arquivo.getId());
				return Response.status(Response.Status.OK).entity("Registro excluído com sucesso" ).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro: \n" + e.getMessage()).build();
		}
	}
}
