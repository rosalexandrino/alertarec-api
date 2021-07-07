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

import entity.Avaliacao;
import http.AvaliacaoHttp;
import repository.AvaliacaoRepository;
import repository.UsuarioRepository;

@Path("/avaliacao")
public class AvaliacaoController {

	private final AvaliacaoRepository repository = new AvaliacaoRepository();
	
	private final UsuarioRepository usuarioRepository = new UsuarioRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Cadastrar(AvaliacaoHttp avaliacaoHttp) {
		
		String message = "";
		Avaliacao avaliacao = new Avaliacao();

		try {

			avaliacao.setNota(avaliacaoHttp.getNota());
			avaliacao.setDescricao(avaliacaoHttp.getDescricao());
			avaliacao.setUsuario(usuarioRepository.selecionarPorEmail(avaliacaoHttp.getUsuarioEmail()));

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Avaliacao>> violations = validator.validate(avaliacao);
			for (ConstraintViolation<Avaliacao> violation : violations) {
			    message += violation.getMessage() + "\n"; 
			}
			if(message != "") {
				return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
			}
			
			repository.Salvar(avaliacao);
			return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso" ).build();			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o registro: " + e.getMessage() ).build();
		}	
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Alterar(AvaliacaoHttp avaliacaoHttp) {
		
		String message = "";
		Avaliacao avaliacao = new Avaliacao();

		try {
			
			AvaliacaoHttp avaliacaoOld = this.selecionarAvaliacaoPorId(avaliacaoHttp.getId());
			if(avaliacaoOld != null) {
				
				avaliacao.setId(avaliacaoOld.getId());
				avaliacao.setNota(avaliacaoHttp.getNota());
				avaliacao.setDescricao(avaliacaoHttp.getDescricao());
				avaliacao.setDataCriacao(avaliacaoOld.getDataCriacao());

				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();
				Set<ConstraintViolation<Avaliacao>> violations = validator.validate(avaliacao);
				for (ConstraintViolation<Avaliacao> violation : violations) {
				    message += violation.getMessage() + "\n"; 
				}
				if(message != "") {
					return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
				}
				repository.Alterar(avaliacao);
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
	public List<AvaliacaoHttp> selecionarTodos() {

		List<AvaliacaoHttp> avaliacaoHttp = new ArrayList<AvaliacaoHttp>();
		List<Avaliacao> avaliacoes = repository.selecionarTodos();
		for (Avaliacao avaliacao : avaliacoes) {
			avaliacaoHttp.add(new AvaliacaoHttp(avaliacao.getId(), avaliacao.getNota(), avaliacao.getDescricao(), avaliacao.getUsuario().getEmail(),
					avaliacao.getDataCriacao(), avaliacao.getDataAtualizacao()));
		}
		return avaliacaoHttp;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/usuario/{usuario}")
	public List<AvaliacaoHttp> selecionarPorUsuario(@PathParam("usuario") String usuarioEmail) {

		List<AvaliacaoHttp> avaliacaoHttp = new ArrayList<AvaliacaoHttp>();
		List<Avaliacao> avaliacoes = repository.selecionarPorUsuario(usuarioEmail);
		for (Avaliacao avaliacao : avaliacoes) {
			avaliacaoHttp.add(new AvaliacaoHttp(avaliacao.getId(), avaliacao.getNota(), avaliacao.getDescricao(), avaliacao.getUsuario().getEmail(),
					avaliacao.getDataCriacao(), avaliacao.getDataAtualizacao()));
		}
		return avaliacaoHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public AvaliacaoHttp selecionarAvaliacaoPorId(@PathParam("id") Long id) {

		Avaliacao avaliacao = repository.selecionarPorId(id);
		if (avaliacao != null) {
			return new AvaliacaoHttp(avaliacao.getId(), avaliacao.getNota(), avaliacao.getDescricao(), avaliacao.getUsuario().getEmail(),
					avaliacao.getDataCriacao(), avaliacao.getDataAtualizacao());
		}
		return null;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response Excluir(@PathParam("id") Long id) {

		try {
			Avaliacao avaliacao = repository.selecionarPorId(id);
			if(avaliacao != null) {
				repository.Excluir(avaliacao.getId());
				return Response.status(Response.Status.OK).entity("Registro excluído com sucesso" ).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro: \n" + e.getMessage()).build();
		}
	}
}
