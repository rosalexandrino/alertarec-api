package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "usuarios")
@NamedQueries(value = { @NamedQuery(name = "Usuario.selecionarTodos", query = "SELECT u FROM Usuario u"),
		@NamedQuery(name = "Usuario.selecionarPorEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email") })
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Email(message = "{usuario.email.invalido}")
	@NotBlank(message = "{usuario.email.vazio}")
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "senha")
	private String senha;

	@NotBlank(message = "{usuario.nome.vazio}")
	@Column(name = "nome")
	private String nome;

	@Pattern(regexp = "[0-9]{11}", message = "{usuario.telefone.invalido}")
	@NotNull(message = "{usuario.telefone.vazio}")
	@Column(name = "telefone")
	private String telefone;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
