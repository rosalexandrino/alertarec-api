package controller;

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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
	public Response Cadastrar(SolicitacaoHttp solicitacaoHttp) {
		
		String message = "";
		Solicitacao solicitacao = new Solicitacao();

		try {

			solicitacao.setDescricao(solicitacaoHttp.getDescricao());
			solicitacao.setPonto(pontoDeRiscoRepository.selecionarPorId(solicitacaoHttp.getPonto()));
			solicitacao.setTipo(tipoDeSolicitacaoRepository.selecionarPorId(solicitacaoHttp.getTipo()));
			solicitacao.setUsuario(usuarioRepository.selecionarPorEmail(solicitacaoHttp.getUsuarioEmail()));
			solicitacao.setDataSolicitacao(solicitacaoHttp.getDataSolicitacao());
			solicitacao.setDataConclusao(solicitacaoHttp.getDataConclusao());
			
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Solicitacao>> violations = validator.validate(solicitacao);
			for (ConstraintViolation<Solicitacao> violation : violations) {
			    message += violation.getMessage() + "\n"; 
			}
			if(message != "") {
				return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
			}

			repository.Salvar(solicitacao);
			return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso" ).build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o registro: " + e.getMessage() ).build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Alterar(SolicitacaoHttp solicitacaoHttp) {
		
		String message = "";
		Solicitacao solicitacao = new Solicitacao();

		try {
			
			Solicitacao solicitacaoOld = repository.selecionarPorId(solicitacaoHttp.getId());
			if(solicitacaoOld != null) {
				
				solicitacao.setId(solicitacaoOld.getId());
				solicitacao.setDescricao(solicitacaoHttp.getDescricao());
				solicitacao.setPonto(pontoDeRiscoRepository.selecionarPorId(solicitacaoHttp.getPonto()));
				solicitacao.setTipo(tipoDeSolicitacaoRepository.selecionarPorId(solicitacaoHttp.getTipo()));
				solicitacao.setUsuario(usuarioRepository.selecionarPorEmail(solicitacaoOld.getUsuario().getEmail()));
				solicitacao.setDataSolicitacao(solicitacaoOld.getDataSolicitacao());
				solicitacao.setDataConclusao(solicitacaoHttp.getDataConclusao());
				solicitacao.setDataCriacao(solicitacaoOld.getDataCriacao());
				
				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();
				Set<ConstraintViolation<Solicitacao>> violations = validator.validate(solicitacao);
				for (ConstraintViolation<Solicitacao> violation : violations) {
				    message += violation.getMessage() + "\n"; 
				}
				if(message != "") {
					return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao alterar o registro: \n" + message ).build();
				}

				repository.Alterar(solicitacao);
				return Response.status(Response.Status.OK).entity("Registro alterado com sucesso" ).build();
			}else {
				return Response.status(Response.Status.NOT_FOUND).entity("Erro ao alterar o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao alterar o registro: \n" + e.getMessage()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<SolicitacaoHttp> selecionarTodos() {

		List<SolicitacaoHttp> solicitacoesHttp = new ArrayList<SolicitacaoHttp>();
		List<Solicitacao> solicitacoes = repository.selecionarTodos();
		for (Solicitacao solicitacao : solicitacoes) {
			solicitacoesHttp.add(new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), 
					solicitacao.getTipo().getId(), solicitacao.getPonto().getId(), solicitacao.getUsuario().getEmail(), 
					solicitacao.getDataSolicitacao(), solicitacao.getDataConclusao()));
		}
		return solicitacoesHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public SolicitacaoHttp selecionarSolicitacaoPorId(@PathParam("id") Long id) {

		Solicitacao solicitacao = repository.selecionarPorId(id);
		if (solicitacao != null) {
			return new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(),
					solicitacao.getPonto().getId(), solicitacao.getUsuario().getEmail(), solicitacao.getDataSolicitacao(), 
					solicitacao.getDataConclusao());
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
			solicitacoesHttp.add(new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(), 
					solicitacao.getPonto().getId(), solicitacao.getUsuario().getEmail(), solicitacao.getDataSolicitacao(), 
					solicitacao.getDataConclusao()));
		}
		return solicitacoesHttp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/usuario/{usuario}")
	public List<SolicitacaoHttp> selecionarSolicitacaoPorUsuario(@PathParam("usuario") String usuarioEmail) {

		List<SolicitacaoHttp> solicitacoesHttp = new ArrayList<SolicitacaoHttp>();
		List<Solicitacao> solicitacoes = repository.selecionarPorUsuario(usuarioEmail);
		for (Solicitacao solicitacao : solicitacoes) {
			solicitacoesHttp.add(new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(),
					solicitacao.getPonto().getId(), solicitacao.getUsuario().getEmail(), solicitacao.getDataSolicitacao(), 
					solicitacao.getDataConclusao()));
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
			solicitacoesHttp.add(new SolicitacaoHttp(solicitacao.getId(), solicitacao.getDescricao(), solicitacao.getTipo().getId(),
					solicitacao.getPonto().getId(), solicitacao.getUsuario().getEmail(), solicitacao.getDataSolicitacao(), 
					solicitacao.getDataConclusao()));
		}
		return solicitacoesHttp;
	}
	
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response Excluir(@PathParam("id") Long id) {

		try {
			Solicitacao solicitacao = repository.selecionarPorId(id);
			if(solicitacao != null) {
				repository.Excluir(solicitacao.getId());
				return Response.status(Response.Status.OK).entity("Registro excluído com sucesso" ).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro: \n" + e.getMessage()).build();
		}
	}
}
