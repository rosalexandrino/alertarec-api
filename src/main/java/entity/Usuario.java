package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
@NamedQueries(value = { @NamedQuery(name = "Usuario.selecionarTodos", query = "SELECT u FROM Usuario u"),
		@NamedQuery(name = "Usuario.selecionarPorEmail", query = "SELECT u FROM Usuario u WHERE u.email = :email") })
public class Usuario extends Entidade{

	@Email(message = "{usuario.email.invalido}")
	@NotBlank(message = "{usuario.email.vazio}")
	@Column(name = "email", unique = true)
	private String email;

	@NotBlank(message = "{usuario.senha.vazio}")
	@Column(name = "senha")
	private String senha;

	@NotBlank(message = "{usuario.nome.vazio}")
	@Pattern(regexp = "^[A-Za-záàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ\\s]+$", message = "{usuario.nome.invalido}")
	@Size(min = 1, max = 220, message = "{usuario.nome.tamanho}")
	@Column(name = "nome")
	private String nome;

	@Pattern(regexp = "[0-9]+$", message = "{usuario.telefone.invalido}")
	@Size(min = 1, max = 15, message = "{usuario.telefone.tamanho}")
	@NotBlank(message = "{usuario.telefone.vazio}")
	@Column(name = "telefone")
	private String telefone;

	@Valid
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PontoDeRisco> pontosDeRisco;
	
	@Valid
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PontoDeApoio> pontosDeApoio;
	
	@Valid
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Avaliacao> avaliacoes;
	
	@Valid
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Solicitacao> solicitacoes;
	
	public Usuario() {
		super();
		this.pontosDeRisco = new ArrayList<>();
		this.pontosDeApoio = new ArrayList<>();
		this.avaliacoes = new ArrayList<>();
		this.solicitacoes = new ArrayList<>();
	}
	
	public Usuario(Long id) {
		super(id);
		this.pontosDeRisco = new ArrayList<>();
		this.pontosDeApoio = new ArrayList<>();
		this.avaliacoes = new ArrayList<>();
		this.solicitacoes = new ArrayList<>();
	}

	public Usuario(Long id, String email, String senha, String nome, String telefone,
			List<PontoDeRisco> pontosDeRisco, List<PontoDeApoio> pontosDeApoio,
			List<Avaliacao> avaliacoes, List<Solicitacao> solicitacoes) {
		super(id);
		this.email = email;
		this.senha = senha;
		this.nome = nome;
		this.telefone = telefone;
		this.pontosDeRisco = pontosDeRisco;
		this.pontosDeApoio = pontosDeApoio;
		this.avaliacoes = avaliacoes;
		this.solicitacoes = solicitacoes;
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

	public List<PontoDeRisco> getPontosDeRisco() {
		return pontosDeRisco;
	}

	public void setPontosDeRisco(List<PontoDeRisco> pontosDeRisco) {
		this.pontosDeRisco = pontosDeRisco;
	}
	
	public List<PontoDeApoio> getPontosDeApoio() {
		return pontosDeApoio;
	}

	public void setPontosDeApoio(List<PontoDeApoio> pontosDeApoio) {
		this.pontosDeApoio = pontosDeApoio;
	}
	
	public List<Avaliacao> getAvaliacoes() {
		return avaliacoes;
	}

	public void setAvaliacoes(List<Avaliacao> avaliacoes) {
		this.avaliacoes = avaliacoes;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
}