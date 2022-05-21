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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import entity.Perfil;
import http.PerfilHttp;
import repository.PerfilRepository;

@Path("/perfil")
public class PerfilController {
	
	private final PerfilRepository repository = new PerfilRepository();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Cadastrar(PerfilHttp perfilHttp) {
		
		String message = "";
		Perfil perfil = new Perfil();

		try {

			perfil.setPerfil(perfilHttp.getPerfil());
			
			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Perfil>> violations = validator.validate(perfil);
			for (ConstraintViolation<Perfil> violation : violations) {
			    message += violation.getMessage() + "\n"; 
			}
			if(message != "") {
				return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message ).build();
			}
			
			repository.Salvar(perfil);
			return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso" ).build();			
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o registro: " + e.getMessage() ).build();
		}	
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<PerfilHttp> selecionarTodos() {

		List<PerfilHttp> perfilsHttp = new ArrayList<PerfilHttp>();
		List<Perfil> perfils = repository.selecionarTodos();
		for (Perfil perfil : perfils) {
			perfilsHttp.add(new PerfilHttp(perfil.getId(), perfil.getPerfil()));
		}
		return perfilsHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/perfil/{perfil}")
	public PerfilHttp selecionarPerfil(@PathParam("perfil") String perfilStr) {

		Perfil perfil = repository.selecionarPorPerfil(perfilStr);
		if (perfil != null) {
			return new PerfilHttp(perfil.getId(), perfil.getPerfil());
		}
		return null;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response Excluir(@PathParam("id") Long id) {

		try {
			Perfil perfil = repository.selecionarPorId(id);
			if(perfil != null) {
				repository.Excluir(perfil.getId());
				
				return Response.status(Response.Status.OK).entity("Registro excluído com sucesso" ).build();				
			}else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro: \n" + e.getMessage()).build();
		}
	}

}
