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

import entity.TipoSolicitacao;
import http.TipoSolicitacaoHttp;
import repository.TipoSolicitacaoRepository;

@Path("/tipoSolicitacao")
public class TipoSolicitacaoController {

	private final TipoSolicitacaoRepository repository = new TipoSolicitacaoRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Cadastrar(TipoSolicitacaoHttp tipoHttp) {
		
		String message = "";
		TipoSolicitacao tipo = new TipoSolicitacao();

		try {

			tipo.setDescricao(tipoHttp.getDescricao());
			
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<TipoSolicitacao>> violations = validator.validate(tipo);
			for (ConstraintViolation<TipoSolicitacao> violation : violations) {
			    message += violation.getMessage() + "\n"; 
			}
			if(message != "") {
				return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
			}
			repository.Salvar(tipo);
			return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso" ).build();			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o registro: " + e.getMessage() ).build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Alterar(TipoSolicitacaoHttp tipoHttp) {
		
		String message = "";
		TipoSolicitacao tipo = new TipoSolicitacao();

		try {
			
			TipoSolicitacaoHttp tipoOld = this.selecionarTipoSolicitacaoPorId(tipoHttp.getId());
			if(tipoOld != null) {
				
				tipo.setId(tipoOld.getId());
				tipo.setDescricao(tipoHttp.getDescricao());
				tipo.setDataCriacao(tipoOld.getDataCriacao());
				
				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();
				Set<ConstraintViolation<TipoSolicitacao>> violations = validator.validate(tipo);
				for (ConstraintViolation<TipoSolicitacao> violation : violations) {
				    message += violation.getMessage() + "\n"; 
				}
				if(message != "") {
					return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
				}

				repository.Alterar(tipo);
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
	public Response Excluir(@PathParam("id") Long id) {

		try {
			TipoSolicitacao tipo = repository.selecionarPorId(id);
			if(tipo != null) {
				repository.Excluir(tipo.getId());
				return Response.status(Response.Status.OK).entity("Registro excluído com sucesso" ).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro: \n" + e.getMessage()).build();
		}
	}
}

