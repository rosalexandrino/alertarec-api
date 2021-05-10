package dto;

public class UsuarioDTO {
	
	private Long id;

	private String email;

	private String nome;

	private String telefone;


	public UsuarioDTO() {
	}

	public UsuarioDTO(Long id, String email, String nome, String telefone) {
		this.id = id;
		this.email = email;
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