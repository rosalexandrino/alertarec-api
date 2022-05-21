package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuario_perfil")
@NamedQueries(value = { @NamedQuery(name = "UsuarioPerfil.selecionarTodos", query = "SELECT u FROM UsuarioPerfil u"),
		@NamedQuery(name = "UsuarioPerfil.selecionarPorEmail", query = "SELECT u FROM UsuarioPerfil u WHERE u.email = :email"),
		@NamedQuery(name = "UsuarioPerfil.selecionarPorPerfil", query = "SELECT u FROM UsuarioPerfil u WHERE u.perfil = :perfil"),
		@NamedQuery(name = "UsuarioPerfil.selecionarPorEmailPerfil", query = "SELECT u FROM UsuarioPerfil u WHERE u.email = :email AND u.perfil = :perfil")})
public class UsuarioPerfil extends Entidade {

	@NotBlank(message = "{usuario_perfil.perfil.vazio}")
	@Size(min = 1, max = 15, message = "{usuario_perfil.perfil.tamanho}")
	@Pattern(regexp = "^[A-Za-z]+$", message = "{usuario_perfil.perfil.invalido}")
	@Column(name = "perfil", length = 15)
	private String perfil;
	
	@Email(message = "{usuario_perfil.email.invalido}")
	@NotBlank(message = "{usuario_perfil.email.vazio}")
	@Column(name = "email")
	private String email;
	
	public UsuarioPerfil() {
		super();
	}

	public UsuarioPerfil(Long id) {
		super(id);
	}
	
	public UsuarioPerfil(Long id, String perfil, String email) {
		super(id);
		this.perfil = perfil;
		this.email = email;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}