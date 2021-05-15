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

import entity.TipoPontoDeRisco;
import http.TipoPontoDeRiscoHttp;
import repository.TipoPontoDeRiscoRepository;

@Path("/tipoPontoRisco")
public class TipoPontoDeRiscoController {

	private final TipoPontoDeRiscoRepository repository = new TipoPontoDeRiscoRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(TipoPontoDeRiscoHttp tipoHttp) {

		TipoPontoDeRisco tipo = new TipoPontoDeRisco();

		try {

			tipo.setDescricao(tipoHttp.getDescricao());

			repository.Salvar(tipo);

			return "Registro cadastrado com sucesso!";

		} catch (Exception e) {

			return "Erro ao cadastrar um registro " + e.getMessage();

		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String Alterar(TipoPontoDeRiscoHttp tipoHttp) {

		TipoPontoDeRisco tipo = new TipoPontoDeRisco();

		try {

			tipo.setId(tipoHttp.getId());
			tipo.setDescricao(tipoHttp.getDescricao());

			repository.Alterar(tipo);

			return "Registro alterado com sucesso!";

		} catch (Exception e) {

			return "Erro ao alterar o registro " + e.getMessage();

		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<TipoPontoDeRiscoHttp> selecionarTodos() {

		List<TipoPontoDeRiscoHttp> tiposHttp = new ArrayList<TipoPontoDeRiscoHttp>();

		List<TipoPontoDeRisco> tipos = repository.selecionarTodos();

		for (TipoPontoDeRisco tipo : tipos) {

			tiposHttp.add(new TipoPontoDeRiscoHttp(tipo.getId(), tipo.getDescricao()));
		}

		return tiposHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public TipoPontoDeRiscoHttp GetContatoPorId(@PathParam("id") Long id) {

		TipoPontoDeRisco tipo = repository.selecionarPorId(id);

		if (tipo != null) {
			return new TipoPontoDeRiscoHttp(tipo.getId(), tipo.getDescricao());
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

