package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioHttp {

	private Long id;

	private String email;

	private String senha;

	private String nome;

	private String telefone;

	public UsuarioHttp() {
	}

	public UsuarioHttp(Long id, String email, String senha, String nome, String telefone) {
		super();
		this.id = id;
		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.telefone = telefone;
	}

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
