package controller;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import entity.PontoDeApoio;
import http.PontoDeApoioHttp;
import repository.PontoDeApoioRepository;
import repository.TipoPontoDeApoioRepository;
import repository.UsuarioRepository;

@Path("/pontoDeApoio")
public class PontoDeApoioController {
	
	private final PontoDeApoioRepository repository = new PontoDeApoioRepository ();

	private final TipoPontoDeApoioRepository tipoDeApoioRepository = new TipoPontoDeApoioRepository();
	
	private final UsuarioRepository usuarioRepository = new UsuarioRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(PontoDeApoioHttp pontoHttp) {

		PontoDeApoio ponto = new PontoDeApoio();

		try {

			ponto.setLatitude(pontoHttp.getLatitude());
			ponto.setLongitude(pontoHttp.getLongitude());
			ponto.setTipoDeApoio(tipoDeApoioRepository.selecionarPorId(pontoHttp.getTipoDeApoio()));
			ponto.setUsuario(usuarioRepository.selecionarPorId(pontoHttp.getUsuario()));

			repository.Salvar(ponto);

			return "Registro cadastrado com sucesso!";

		} catch (Exception e) {

			return "Erro ao cadastrar um registro " + e.getMessage();

		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String Alterar(PontoDeApoioHttp pontoHttp) {

		PontoDeApoio ponto = new PontoDeApoio();

		try {
			
			PontoDeApoioHttp pontoOld = this.selecionarPontoDeApoioPorId(pontoHttp.getId());
			if(pontoOld != null) {
				
				ponto.setId(pontoOld.getId());
				ponto.setLatitude(pontoHttp.getLatitude());
				ponto.setLongitude(pontoHttp.getLongitude());
				ponto.setTipoDeApoio(tipoDeApoioRepository.selecionarPorId(pontoHttp.getTipoDeApoio()));
				ponto.setUsuario(usuarioRepository.selecionarPorId(pontoOld.getUsuario()));

				repository.Alterar(ponto);

				return "Registro alterado com sucesso!";
			}else {
				return "Erro ao alterar o registro";
			}

		} catch (Exception e) {

			return "Erro ao alterar o registro " + e.getMessage();

		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<PontoDeApoioHttp> selecionarTodos() {

		List<PontoDeApoioHttp> pontosHttp = new ArrayList<PontoDeApoioHttp>();

		List<PontoDeApoio> pontos = repository.selecionarTodos();

		for (PontoDeApoio ponto : pontos) {

			pontosHttp.add(new PontoDeApoioHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeApoio().getId(), ponto.getUsuario().getId()));
		}

		return pontosHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public PontoDeApoioHttp selecionarPontoDeApoioPorId(@PathParam("id") Long id) {

		PontoDeApoio ponto = repository.selecionarPorId(id);

		if (ponto != null) {
			return new PontoDeApoioHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeApoio().getId(), ponto.getUsuario().getId());
		}
		return null;
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