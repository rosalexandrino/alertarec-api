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

import entity.Usuario;
import http.UsuarioHttp;
import repository.UsuarioRepository;
import util.Authentication;

@Path("/usuario")
public class UsuarioController {

	private final UsuarioRepository repository = new UsuarioRepository();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String Cadastrar(UsuarioHttp usuarioHttp) {

		Usuario usuario = new Usuario();

		try {
			
			if(usuarioHttp.getSenha() != null) {
				usuarioHttp.setSenha(Authentication.encodeSHA256(usuarioHttp.getSenha()));
			}

			usuario.setEmail(usuarioHttp.getEmail());
			usuario.setNome(usuarioHttp.getNome());
			usuario.setSenha(usuarioHttp.getSenha());
			usuario.setTelefone(usuarioHttp.getTelefone());

			repository.Salvar(usuario);

			return "Registro cadastrado com sucesso!";

		} catch (Exception e) {

			return "Erro ao cadastrar um registro " + e.getMessage();

		}

	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public String Alterar(UsuarioHttp usuarioHttp) {

		Usuario usuario = new Usuario();

		try {
			
			UsuarioHttp usuarioOld = this.GetPessoaPorId(usuarioHttp.getId());
			if(usuarioOld != null) {
				
				usuario.setId(usuarioOld.getId());
				usuario.setEmail(usuarioOld.getEmail());
				if(usuarioHttp.getSenha() != null && usuarioHttp.getSenha() != usuarioOld.getSenha()) {
					usuario.setSenha(Authentication.encodeSHA256(usuarioHttp.getSenha()));
				}else {
					usuario.setSenha(usuarioOld.getSenha());
				}
				
				usuario.setNome(usuarioHttp.getNome());
				usuario.setTelefone(usuarioHttp.getTelefone());

				repository.Alterar(usuario);

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
	public List<UsuarioHttp> selecionarTodos() {

		List<UsuarioHttp> usuariosHttp = new ArrayList<UsuarioHttp>();

		List<Usuario> usuarios = repository.selecionarTodos();

		for (Usuario usuario : usuarios) {

			usuariosHttp.add(new UsuarioHttp(usuario.getId(), usuario.getEmail(), usuario.getSenha(), usuario.getNome(),
					usuario.getTelefone()));
		}

		return usuariosHttp;

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/email/{email}")
	public UsuarioHttp GetPessoa(@PathParam("email") String email) {

		Usuario usuario = repository.selecionarPorEmail(email);

		if (usuario != null) {
			return new UsuarioHttp(usuario.getId(), usuario.getEmail(), usuario.getSenha(), usuario.getNome(),
					usuario.getTelefone());
		}
		return null;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/id/{id}")
	public UsuarioHttp GetPessoaPorId(@PathParam("id") Long id) {

		Usuario usuario = repository.selecionarPorId(id);

		if (usuario != null) {
			return new UsuarioHttp(usuario.getId(), usuario.getEmail(), usuario.getSenha(), usuario.getNome(),
					usuario.getTelefone());
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