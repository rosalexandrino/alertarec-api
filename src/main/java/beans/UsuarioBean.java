package beans;


import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import dto.UsuarioCadastroDTO;
import dto.UsuarioDTO;
import mapper.UsuarioMapper;
import model.Usuario;
import persistence.UsuarioPersistencia;
import util.Authentication;
import util.ResponseMessage;
import util.ResponseMessage.ResponseStatus;

public class UsuarioBean {

	private UsuarioPersistencia usuarioPersistencia = new UsuarioPersistencia();

	public Response cadastrarUsuario(UsuarioCadastroDTO usuarioCadastroDTO) {
		try {
			Usuario usuario = UsuarioMapper.usuarioCadastroDTOParaUsuario(usuarioCadastroDTO);
			try {
				usuario.setSenha(Authentication.encodeSHA256(usuario.getSenha()));
			} catch (Exception ex) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
				ex.printStackTrace();
			}
			this.usuarioPersistencia.salvar(usuario);
			return Response.status(Response.Status.CREATED).build();
		} catch (ConstraintViolationException ex) {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setStatus(ResponseStatus.FALHA);
			responseMessage.setMensagem("Ocorreu um erro na validação dos campos!");
			responseMessage.setInfo(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList()));
			return Response.status(Response.Status.BAD_REQUEST).entity(responseMessage).build();
		} catch (PersistenceException ex) {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setStatus(ResponseStatus.FALHA);
			responseMessage.setMensagem(ex.getMessage());
			return Response.status(Response.Status.CONFLICT).entity(responseMessage).build();
		}
	}

	public Response selecionarUsuario(HttpServletRequest req) {
		Usuario usuario = this.usuarioPersistencia.selecionaPorEmail(req.getUserPrincipal().getName());
		UsuarioDTO usuarioDTO = UsuarioMapper.usuarioParaUsuarioDTO(usuario);
		return Response.ok().entity(usuarioDTO).build();
	}

	public Response deletarUsuario(HttpServletRequest req) {
		String email = req.getUserPrincipal().getName();
		Usuario usuario = this.usuarioPersistencia.selecionaPorEmail(email);
		if (usuario == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
		this.usuarioPersistencia.remover(usuario);
		return Response.noContent().build();
	}

	public Response atualizarUsuario(UsuarioCadastroDTO usuarioCadastroDTO, HttpServletRequest req) {
		try {
			Usuario usuario = this.usuarioPersistencia.selecionaPorEmail(req.getUserPrincipal().getName());
			if (usuarioCadastroDTO.getSenha() != null) {
				try {
					usuario.setSenha(Authentication.encodeSHA256(usuarioCadastroDTO.getSenha()));
				} catch (Exception ex) {
					Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
					ex.printStackTrace();
				}
			}
			usuario.setNome(usuarioCadastroDTO.getNome());
			usuario.setTelefone(usuarioCadastroDTO.getTelefone());
			usuario.setEmail(usuarioCadastroDTO.getEmail());
			this.usuarioPersistencia.atualizar(usuario);
			return Response.noContent().build();
		} catch (ConstraintViolationException ex) {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setStatus(ResponseStatus.FALHA);
			responseMessage.setMensagem("Ocorreu um erro na validação dos campos!");
			responseMessage.setInfo(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage)
					.collect(Collectors.toList()));
			return Response.status(Response.Status.BAD_REQUEST).entity(responseMessage).build();
		}
	}
}
