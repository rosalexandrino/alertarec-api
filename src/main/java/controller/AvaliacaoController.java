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

import entity.Avaliacao;
import http.AvaliacaoHttp;
import repository.AvaliacaoRepository;

@Path("/avaliacao")
public class AvaliacaoController {

	private final AvaliacaoRepository repository = new AvaliacaoRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(AvaliacaoHttp avaliacaoHttp) {

		Avaliacao avaliacao = new Avaliacao();

		try {

			avaliacao.setNota(avaliacaoHttp.getNota());
			avaliacao.setDescricao(avaliacaoHttp.getDescricao());

			repository.Salvar(avaliacao);

			return "Registro cadastrado com sucesso!";

		} catch (Exception e) {

			return "Erro ao cadastrar um registro " + e.getMessage();

		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String Alterar(AvaliacaoHttp avaliacaoHttp) {

		Avaliacao avaliacao = new Avaliacao();

		try {
			
			AvaliacaoHttp avaliacaoOld = this.selecionarAvaliacaoPorId(avaliacaoHttp.getId());
			if(avaliacaoOld != null) {
				
				avaliacao.setId(avaliacaoOld.getId());
				avaliacao.setNota(avaliacaoHttp.getNota());
				avaliacao.setDescricao(avaliacaoHttp.getDescricao());

				repository.Alterar(avaliacao);

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
	public List<AvaliacaoHttp> selecionarTodos() {

		List<AvaliacaoHttp> avaliacaoHttp = new ArrayList<AvaliacaoHttp>();

		List<Avaliacao> avaliacoes = repository.selecionarTodos();

		for (Avaliacao avaliacao : avaliacoes) {

			avaliacaoHttp.add(new AvaliacaoHttp(avaliacao.getId(), avaliacao.getNota(), avaliacao.getDescricao()));
		}

		return avaliacaoHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public AvaliacaoHttp selecionarAvaliacaoPorId(@PathParam("id") Long id) {

		Avaliacao avaliacao = repository.selecionarPorId(id);

		if (avaliacao != null) {
			return new AvaliacaoHttp(avaliacao.getId(), avaliacao.getNota(), avaliacao.getDescricao());
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
