package controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
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

import javax.validation.Validator;

import entity.Perfil;
import entity.Usuario;
import http.UsuarioHttp;
import http.UsuarioPerfilHttp;
import repository.PerfilRepository;
import repository.UsuarioRepository;
import util.Authentication;

@Path("/usuario")
public class UsuarioController {

	private final UsuarioRepository repository = new UsuarioRepository();

	private final PerfilRepository perfilRepository = new PerfilRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Cadastrar(UsuarioHttp usuarioHttp) {

		Usuario usuario = new Usuario();
		String message = "";

		try {

			if (usuarioHttp.getSenha() != null) {
				usuarioHttp.setSenha(Authentication.encodeSHA256(usuarioHttp.getSenha()));
			}

			usuario.setEmail(usuarioHttp.getEmail());
			usuario.setNome(usuarioHttp.getNome());
			usuario.setSenha(usuarioHttp.getSenha());
			usuario.setTelefone(usuarioHttp.getTelefone());
			
			Set<Perfil> perfilSet = new HashSet<>();
			
			Perfil perfil = perfilRepository.selecionarPorPerfil("GERAL");
			if (perfil != null) {
				perfilSet.add(perfil);
			}
			
			usuario.setPerfis(perfilSet);

			ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
			Validator validator = factory.getValidator();
			Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
			for (ConstraintViolation<Usuario> violation : violations) {
				message += violation.getMessage() + "\n";
			}
			if (message != "") {
				return Response.status(Response.Status.BAD_REQUEST).entity("Erro ao cadastrar o registro: \n" + message)
						.build();
			}
			
			repository.Salvar(usuario);
			return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso").build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao cadastrar o registro: " + e.getMessage()).build();
		}
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Alterar(UsuarioHttp usuarioHttp) {

		String message = "";
		Usuario usuario = new Usuario();

		try {

			Usuario usuarioOld = repository.selecionarPorEmail(usuarioHttp.getEmail());
			if (usuarioOld != null) {

				usuario.setId(usuarioOld.getId());
				usuario.setEmail(usuarioOld.getEmail());
				String newPassword = Authentication.encodeSHA256(usuarioHttp.getSenha());
				if (usuarioHttp.getSenha() != null && newPassword != usuarioOld.getSenha()) {
					usuario.setSenha(newPassword);
				} else {
					usuario.setSenha(usuarioOld.getSenha());
				}

				usuario.setNome(usuarioHttp.getNome());
				usuario.setTelefone(usuarioHttp.getTelefone());
				usuario.setDataCriacao(usuarioOld.getDataCriacao());

				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();
				Set<ConstraintViolation<Usuario>> violations = validator.validate(usuario);
				for (ConstraintViolation<Usuario> violation : violations) {
					message += violation.getMessage() + "\n";
				}
				if (message != "") {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Erro ao alterar o registro: \n" + message).build();
				}

				repository.Alterar(usuario);
				return Response.status(Response.Status.OK).entity("Registro alterado com sucesso").build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Erro ao alterar o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao alterar o registro: \n" + e.getMessage()).build();
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/perfil")
	public Response Alterar(UsuarioPerfilHttp usuarioPerfilHttp) {

		String message = "";

		try {

			Usuario usuarioOld = repository.selecionarPorEmail(usuarioPerfilHttp.getEmail());
			if (usuarioOld != null) {
				
				Set<Perfil> perfisNew = new HashSet<>();
				for (String perfil : usuarioPerfilHttp.getPerfis() ) {
					Perfil perfilOld = perfilRepository.selecionarPorPerfil(perfil);
					if(perfilOld != null) {
						perfisNew.add(perfilOld);
					}
				}
				
				usuarioOld.setPerfis(perfisNew);

				ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
				Validator validator = factory.getValidator();
				Set<ConstraintViolation<Usuario>> violations = validator.validate(usuarioOld);
				for (ConstraintViolation<Usuario> violation : violations) {
					message += violation.getMessage() + "\n";
				}
				if (message != "") {
					return Response.status(Response.Status.BAD_REQUEST)
							.entity("Erro ao alterar o registro: \n" + message).build();
				}

				repository.Alterar(usuarioOld);
				return Response.status(Response.Status.OK).entity("Registro alterado com sucesso").build();
			} else {
				return Response.status(Response.Status.NOT_FOUND).entity("Erro ao alterar o registro").build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao alterar o registro: \n" + e.getMessage()).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/todos")
	public List<UsuarioHttp> selecionarTodos() {

		List<UsuarioHttp> usuariosHttp = new ArrayList<UsuarioHttp>();
		List<Usuario> usuarios = repository.selecionarTodos();
		for (Usuario usuario : usuarios) {
			usuariosHttp.add(
					new UsuarioHttp(usuario.getEmail(), usuario.getSenha(), usuario.getNome(), usuario.getTelefone()));
		}
		return usuariosHttp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/email/{email}")
	public UsuarioHttp GetUsuario(@PathParam("email") String email) {

		Usuario usuario = repository.selecionarPorEmail(email);
		if (usuario != null) {
			return new UsuarioHttp(usuario.getEmail(), usuario.getSenha(), usuario.getNome(), usuario.getTelefone());
		}
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/perfil/{email}")
	public UsuarioPerfilHttp GetUsuarioPerfil(@PathParam("email") String email) {

		Usuario usuario = repository.selecionarPorEmail(email);
		if (usuario != null) {
			
			Set<String> perfisStr = new HashSet<>();
			for (Perfil perfil : usuario.getPerfis() ) {
				perfisStr.add(perfil.getPerfil());
			}
			return new UsuarioPerfilHttp(usuario.getEmail(), perfisStr);
		}
		return null;
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{email}")
	public Response Excluir(@PathParam("email") String email) {

		try {
			Usuario usuario = repository.selecionarPorEmail(email);
			if (usuario != null) {
				repository.Excluir(usuario.getId());
				return Response.status(Response.Status.OK).entity("Registro excluído com sucesso").build();
			} else {
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao excluir o registro")
						.build();
			}
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao excluir o registro: \n" + e.getMessage()).build();
		}
	}

}