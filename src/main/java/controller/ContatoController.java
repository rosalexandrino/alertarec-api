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

import entity.Contato;
import http.ContatoHttp;
import repository.ContatoRepository;

@Path("/contato")
public class ContatoController {

	private final ContatoRepository repository = new ContatoRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Cadastrar(ContatoHttp contatoHttp) {
		
		String message = "";
		Contato contato = new Contato();

		try {

			contato.setDescricao(contatoHttp.getDescricao());
			contato.setTelefone(contatoHttp.getTelefone());

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Contato>> violations = validator.validate(contato);
			for (ConstraintViolation<Contato> violation : violations) {
			    message += violation.getMessage() + "\n"; 
			}
			if(message != "") {
				return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
			}
			
			repository.Salvar(contato);
			return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso" ).build();			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o registro: " + e.getMessage() ).build();
		}	
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Alterar(ContatoHttp contatoHttp) {
		
		String message = "";
		Contato contato = new Contato();

		try {

			Contato contatoOld = repository.selecionarPorId(contatoHttp.getId());
			if(contatoOld != null) {
				
				contato.setId(contatoOld.getId());
				contato.setDescricao(contatoHttp.getDescricao());
				contato.setTelefone(contatoHttp.getTelefone());
				contato.setDataCriacao(contatoOld.getDataCriacao());

				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();
				Set<ConstraintViolation<Contato>> violations = validator.validate(contato);
				for (ConstraintViolation<Contato> violation : violations) {
				    message += violation.getMessage() + "\n"; 
				}
				if(message != "") {
					return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
				}
				repository.Alterar(contato);
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
	public List<ContatoHttp> selecionarTodos() {

		List<ContatoHttp> contatosHttp = new ArrayList<ContatoHttp>();
		List<Contato> contatos = repository.selecionarTodos();
		for (Contato contato : contatos) {
			contatosHttp.add(new ContatoHttp(contato.getId(), contato.getDescricao(), contato.getTelefone()));
		}
		return contatosHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public ContatoHttp selecionarContatoPorId(@PathParam("id") Long id) {

		Contato contato = repository.selecionarPorId(id);
		if (contato != null) {
			return new ContatoHttp(contato.getId(), contato.getDescricao(), contato.getTelefone());
		}
		return null;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response Excluir(@PathParam("id") Long id) {

		try {
			Contato contato = repository.selecionarPorId(id);
			if(contato != null) {
				repository.Excluir(contato.getId());
				return Response.status(Response.Status.OK).entity("Registro excluído com sucesso" ).build();
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro: \n" + e.getMessage()).build();
		}
	}

}
