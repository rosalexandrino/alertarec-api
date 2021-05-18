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

import entity.PontoDeRisco;
import http.PontoDeRiscoHttp;
import repository.PontoDeRiscoRepository;
import repository.TipoPontoDeRiscoRepository;
import repository.UsuarioRepository;

@Path("/pontoDeRisco")
public class PontoDeRiscoController {
	
	private final PontoDeRiscoRepository repository = new PontoDeRiscoRepository ();

	private final TipoPontoDeRiscoRepository tipoDeRiscoRepository = new TipoPontoDeRiscoRepository();
	
	private final UsuarioRepository usuarioRepository = new UsuarioRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(PontoDeRiscoHttp pontoHttp) {

		PontoDeRisco ponto = new PontoDeRisco();

		try {

			ponto.setLatitude(pontoHttp.getLatitude());
			ponto.setLongitude(pontoHttp.getLongitude());
			ponto.setTipoDeRisco(tipoDeRiscoRepository.selecionarPorId(pontoHttp.getTipoDeRisco()));
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
	public String Alterar(PontoDeRiscoHttp pontoHttp) {

		PontoDeRisco ponto = new PontoDeRisco();

		try {
			
			PontoDeRiscoHttp pontoOld = this.selecionarPontoDeRiscoPorId(pontoHttp.getId());
			if(pontoOld != null) {
				
				ponto.setId(pontoOld.getId());
				ponto.setLatitude(pontoHttp.getLatitude());
				ponto.setLongitude(pontoHttp.getLongitude());
				ponto.setTipoDeRisco(tipoDeRiscoRepository.selecionarPorId(pontoHttp.getTipoDeRisco()));
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
	public List<PontoDeRiscoHttp> selecionarTodos() {

		List<PontoDeRiscoHttp> pontosHttp = new ArrayList<PontoDeRiscoHttp>();

		List<PontoDeRisco> pontos = repository.selecionarTodos();

		for (PontoDeRisco ponto : pontos) {

			pontosHttp.add(new PontoDeRiscoHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeRisco().getId(), ponto.getUsuario().getId()));
		}

		return pontosHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public PontoDeRiscoHttp selecionarPontoDeRiscoPorId(@PathParam("id") Long id) {

		PontoDeRisco ponto = repository.selecionarPorId(id);

		if (ponto != null) {
			return new PontoDeRiscoHttp(ponto.getId(), ponto.getLongitude(), ponto.getLatitude(), ponto.getTipoDeRisco().getId(), ponto.getUsuario().getId());
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
