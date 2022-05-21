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
import entity.Usuario;
import entity.UsuarioPerfil;
import http.UsuarioPerfilHttp;
import repository.PerfilRepository;
import repository.UsuarioPerfilRepository;
import repository.UsuarioRepository;

@Path("/usuario_perfil")
public class UsuarioPerfilController {

	private final UsuarioPerfilRepository repository = new UsuarioPerfilRepository();

	private final PerfilRepository perfilRepository = new PerfilRepository();

	private final UsuarioRepository usuarioRepository = new UsuarioRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response Cadastrar(UsuarioPerfilHttp usuarioPerfilHttp) {

		String message = "";

		try {

			Perfil perfil = perfilRepository.selecionarPorPerfil(usuarioPerfilHttp.getPerfil());
			Usuario usuario = usuarioRepository.selecionarPorEmail(usuarioPerfilHttp.getEmail());
			if (perfil != null && usuario != null) {

				UsuarioPerfil usuarioPerfilOld = repository.selecionarPorEmailPerfil(usuarioPerfilHttp.getEmail(), usuarioPerfilHttp.getPerfil());
				if (usuarioPerfilOld == null) {
					UsuarioPerfil newUsuarioPerfil = new UsuarioPerfil();
					newUsuarioPerfil.setEmail(usuarioPerfilHttp.getEmail());
					newUsuarioPerfil.setPerfil(usuarioPerfilHttp.getPerfil());

					ValidatorFactory factoryUsuarioPerfil = Validation.buildDefaultValidatorFactory();
					Validator validatorUsuarioPerfil = factoryUsuarioPerfil.getValidator();
					Set<ConstraintViolation<UsuarioPerfil>> violationsUsuarioPerfil = validatorUsuarioPerfil
							.validate(newUsuarioPerfil);
					for (ConstraintViolation<UsuarioPerfil> violation : violationsUsuarioPerfil) {
						message += violation.getMessage() + "\n";
					}
					if (message != "") {
						return Response.status(Response.Status.BAD_REQUEST)
								.entity("Erro ao cadastrar o registro: \n" + message).build();
					}
					repository.Salvar(newUsuarioPerfil);
					return Response.status(Response.Status.OK).entity("Registro cadastrado com sucesso").build();
				}
			}
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Erro ao cadastrar o registro")
 					.build();
		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
					.entity("Erro ao cadastrar o registro: " + e.getMessage()).build();
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/email/{email}")
	public List<UsuarioPerfilHttp> GetUsuarioPerfil(@PathParam("email") String email) {
		
		List<UsuarioPerfilHttp> usuariosPerfilHttp = new ArrayList<UsuarioPerfilHttp>();
		List<UsuarioPerfil> usuariosPerfil = repository.selecionarPorEmail(email);
		for (UsuarioPerfil usuarioPerfil : usuariosPerfil) {
			usuariosPerfilHttp.add(
					new UsuarioPerfilHttp(usuarioPerfil.getEmail(), usuarioPerfil.getPerfil()));
		}
		return usuariosPerfilHttp;
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response Excluir(@PathParam("id") Long id) {

		try {
			UsuarioPerfil usuarioPerfil = repository.selecionarPorId(id);
			if (usuarioPerfil != null) {
				repository.Excluir(usuarioPerfil.getId());
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
