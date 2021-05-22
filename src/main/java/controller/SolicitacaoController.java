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

import entity.Solicitacao;
import http.SolicitacaoHttp;
import repository.PontoDeRiscoRepository;
import repository.SolicitacaoRepository;
import repository.TipoSolicitacaoRepository;
import repository.UsuarioRepository;

@Path("/solicitacao")
public class SolicitacaoController {
	
	private final SolicitacaoRepository repository = new SolicitacaoRepository();

	private final PontoDeRiscoRepository pontoDeRiscoRepository = new PontoDeRiscoRepository ();

	private final TipoSolicitacaoRepository tipoDeSolicitacaoRepository = new TipoSolicitacaoRepository();
	
	private final UsuarioRepository usuarioRepository = new UsuarioRepository();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(SolicitacaoHttp solicitacaoHttp) {

		Solicitacao solicitacao = new Solicitacao();

		try {

			solicitacao.setDescricao(solicitacaoHttp.getDescricao());
			solicitacao.setPonto(pontoDeRiscoRepository.selecionarPorId(solicitacaoHttp.getPonto()));
			solicitacao.setTipo(tipoDeSolicitacaoRepository.selecionarPorId(solicitacaoHttp.getTipo()));
			solicitacao.setUsuario(usuarioRepository.selecionarPorId(solicitacaoHttp.getUsuario()));
			solicitacao.setDataSolicitacao(solicitacaoHttp.getDataSolicitacao());
			solicitacao.setDataConclusao(solicitacaoHttp.getDataConclusao());

			repository.Salvar(solicitacao);

			return "Registro cadastrado com sucesso!";

		} catch (Exception e) {

			return "Erro ao cadastrar um registro " + e.getMessage();

		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String Alterar(SolicitacaoHttp solicitacaoHttp) {

		Solicitacao solicitacao = new Solicitacao();

		try {
			
			SolicitacaoHttp solicitacaoOld = this.selecionarSolicitacaoPorId(solicitacaoHttp.getId());
			if(solicitacaoOld != null) {
				
				solicitacao.setId(solicitacaoOld.getId());
				solicitacao.setDescricao(solicitacaoHttp.getDescricao());
				solicitacao.setPonto(pontoDeRiscoRepository.selecionarPorId(solicitacaoHttp.getPonto()));
				solicitacao.setTipo(tipoDeSolicitacaoRepository.selecionarPorId(solicitacaoHttp.getTipo()));
				solicitacao.setUsuario(usuarioRepository.selecionarPorId(solicitacaoOld.getUsuario()));
				solicitacao.setDataSolicitacao(solicitacaoOld.getDataSolicitacao());
				solicitacao.setDataConclusao(solicitacaoHttp.getDataConclusao());

				repository.Alterar(solicitacao);

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
	public List<SolicitacaoHttp> selecionarTodos() {

		List<SolicitacaoHttp> solicitacoesHttp = new ArrayList<SolicitacaoHttp>();

		List<Solicitacao> solicitacoes = repository.selecionarTodos();

		for (Solicitacao solicitacao : solicitacoes) {

			solicitacoesHttp.add(new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(), solicitacao.getPonto().getId(), solicitacao.getUsuario().getId(), solicitacao.getDataSolicitacao(), solicitacao.getDataConclusao()));
		}

		return solicitacoesHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public SolicitacaoHttp selecionarSolicitacaoPorId(@PathParam("id") Long id) {

		Solicitacao solicitacao = repository.selecionarPorId(id);

		if (solicitacao != null) {
			return new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(), solicitacao.getPonto().getId(), solicitacao.getUsuario().getId(), solicitacao.getDataSolicitacao(), solicitacao.getDataConclusao());
		}
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tipo/{tipo}")
	public List<SolicitacaoHttp> selecionarSolicitacaoPorTipo(@PathParam("tipo") Long tipo) {

		List<SolicitacaoHttp> solicitacoesHttp = new ArrayList<SolicitacaoHttp>();
		
		List<Solicitacao> solicitacoes = repository.selecionarPorTipo(tipo);
		
		for (Solicitacao solicitacao : solicitacoes) {

			solicitacoesHttp.add(new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(), solicitacao.getPonto().getId(), solicitacao.getUsuario().getId(), solicitacao.getDataSolicitacao(), solicitacao.getDataConclusao()));
		}

		return solicitacoesHttp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/usuario/{usuario}")
	public List<SolicitacaoHttp> selecionarSolicitacaoPorUsuario(@PathParam("usuario") Long usuario) {

		List<SolicitacaoHttp> solicitacoesHttp = new ArrayList<SolicitacaoHttp>();
		
		List<Solicitacao> solicitacoes = repository.selecionarPorUsuario(usuario);
		
		for (Solicitacao solicitacao : solicitacoes) {

			solicitacoesHttp.add(new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(), solicitacao.getPonto().getId(), solicitacao.getUsuario().getId(), solicitacao.getDataSolicitacao(), solicitacao.getDataConclusao()));
		}

		return solicitacoesHttp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/abertas")
	public List<SolicitacaoHttp> selecionarSolicitacaoAberta() {

		List<SolicitacaoHttp> solicitacoesHttp = new ArrayList<SolicitacaoHttp>();
		
		List<Solicitacao> solicitacoes = repository.selecionarAbertas();
		
		for (Solicitacao solicitacao : solicitacoes) {

			solicitacoesHttp.add(new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(), solicitacao.getPonto().getId(), solicitacao.getUsuario().getId(), solicitacao.getDataSolicitacao(), solicitacao.getDataConclusao()));
		}

		return solicitacoesHttp;
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
