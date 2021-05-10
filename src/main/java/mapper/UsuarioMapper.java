package mapper;

import dto.UsuarioCadastroDTO;
import dto.UsuarioDTO;
import model.Usuario;

public class UsuarioMapper {

	public static Usuario usuarioCadastroDTOParaUsuario(UsuarioCadastroDTO usuarioCadastroDTO) {
		return Usuario.builder().email(usuarioCadastroDTO.getEmail()).senha(usuarioCadastroDTO.getSenha())
				.nome(usuarioCadastroDTO.getNome()).telefone(usuarioCadastroDTO.getTelefone()).build();
	}

	public static UsuarioDTO usuarioParaUsuarioDTO(Usuario usuario) {

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setId(usuario.getId());
		usuarioDTO.setEmail(usuario.getEmail());
		usuarioDTO.setNome(usuario.getNome());
		usuarioDTO.setTelefone(usuario.getTelefone());

		return usuarioDTO;
	}
}
