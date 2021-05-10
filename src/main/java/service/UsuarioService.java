package service;


import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import beans.UsuarioBean;
import dto.UsuarioCadastroDTO;

@Path("/usuario")
public class UsuarioService {

	@EJB
	private UsuarioBean usuarioBean;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cadastrarUsuario(UsuarioCadastroDTO usuarioCadastroDTO) {
		return this.usuarioBean.cadastrarUsuario(usuarioCadastroDTO);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response selecionarUsuario(@Context HttpServletRequest req) {
		return this.usuarioBean.selecionarUsuario(req);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletarUsuario(@Context HttpServletRequest req) {
		return this.usuarioBean.deletarUsuario(req);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarUsuario(UsuarioCadastroDTO usuarioCadastroDTO, @Context HttpServletRequest req) {
		return this.usuarioBean.atualizarUsuario(usuarioCadastroDTO, req);
	}
}