package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
@NamedQueries(value = { @NamedQuery(name = "Usuario.selecionaTodos", query = "SELECT u FROM Usuario u"),
		@NamedQuery(name = "Usuario.selecionaPorEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email") })
public class Usuario extends Entidade {

	private static final long serialVersionUID = 1L;

	@Email(message = "{usuario.email.invalido}")
	@NotBlank(message = "{usuario.email.vazio}")
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "senha")
	private String senha;

	@Size(min = 3, max = 250, message = "{usuario.nome.tamanho}")
	@NotBlank(message = "{usuario.nome.vazio}")
	@Column(name = "nome")
	private String nome;

	@Pattern(regexp = "[0-9]{11}", message = "{usuario.telefone.invalido}")
	@NotNull(message = "{usuario.telefone.vazio}")
	@Column(name = "telefone")
	private String telefone;

	public static class UsuarioBuilder {
		private Long id;

		private String email;

		private String senha;

		private String nome;

		private String telefone;

		public UsuarioBuilder() {
		}

		public UsuarioBuilder id(Long id) {
			this.id = id;
			return this;
		}

		public UsuarioBuilder email(String email) {
			this.email = email;
			return this;
		}

		public UsuarioBuilder senha(String senha) {
			this.senha = senha;
			return this;
		}

		public UsuarioBuilder nome(String nome) {
			this.nome = nome;
			return this;
		}

		public UsuarioBuilder telefone(String telefone) {
			this.telefone = telefone;
			return this;
		}

		public Usuario build() {
			return new Usuario(id, email, senha, nome, telefone);
		}

	}

	public static UsuarioBuilder builder() {
		return new UsuarioBuilder();
	}

	public Usuario() {
	}

	public Usuario(Long id, String email, String senha, String nome, String telefone) {
		super(id);
		this.email = email;
		this.senha = senha;
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}