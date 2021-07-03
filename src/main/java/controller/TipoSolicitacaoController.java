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

import entity.TipoSolicitacao;
import http.TipoSolicitacaoHttp;
import repository.TipoSolicitacaoRepository;

@Path("/tipoSolicitacao")
public class TipoSolicitacaoController {

	private final TipoSolicitacaoRepository repository = new TipoSolicitacaoRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(TipoSolicitacaoHttp tipoHttp) {

		TipoSolicitacao tipo = new TipoSolicitacao();

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
	public String Alterar(TipoSolicitacaoHttp tipoHttp) {

		TipoSolicitacao tipo = new TipoSolicitacao();

		try {
			
			TipoSolicitacaoHttp tipoOld = this.selecionarTipoSolicitacaoPorId(tipoHttp.getId());
			if(tipoOld != null) {
				
				tipo.setId(tipoOld.getId());
				tipo.setDescricao(tipoHttp.getDescricao());
				tipo.setDataCriacao(tipoOld.getDataCriacao());

				repository.Alterar(tipo);

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
	public List<TipoSolicitacaoHttp> selecionarTodos() {

		List<TipoSolicitacaoHttp> tiposHttp = new ArrayList<TipoSolicitacaoHttp>();

		List<TipoSolicitacao> tipos = repository.selecionarTodos();

		for (TipoSolicitacao tipo : tipos) {

			tiposHttp.add(new TipoSolicitacaoHttp(tipo.getId(), tipo.getDescricao(), tipo.getDataCriacao(), tipo.getDataAtualizacao()));
		}

		return tiposHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public TipoSolicitacaoHttp selecionarTipoSolicitacaoPorId(@PathParam("id") Long id) {

		TipoSolicitacao tipo = repository.selecionarPorId(id);

		if (tipo != null) {
			return new TipoSolicitacaoHttp(tipo.getId(), tipo.getDescricao(), tipo.getDataCriacao(), tipo.getDataAtualizacao());
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

