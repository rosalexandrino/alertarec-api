package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "avaliacoes")
@NamedQueries(value = { @NamedQuery(name = "Avaliacao.selecionarTodos", query = "SELECT a FROM Avaliacao a"),
		@NamedQuery(name = "Avaliacao.selecionarPorUsuario", query = "SELECT a FROM Avaliacao a WHERE a.usuario IN (SELECT u FROM Usuario u WHERE u.email = :email)")})
public class Avaliacao extends Entidade{
	
	@Pattern(regexp = "[1-5]{1}", message = "{avaliacao.nota.invalido}")
	@NotBlank(message = "{avaliacao.nota.vazio}")
	@Column(name = "nota")
	private String nota;
	
	@NotBlank(message = "{avaliacao.descricao.vazio}")
	@Pattern(regexp = "^[A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ\\s]+$", message = "{avaliacao.descricao.invalido}")
	@Size(min = 1, max = 220, message = "{avaliacao.descricao.tamanho}")
	@Column(name = "descricao", length = 220)
	private String descricao;
	
	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	public Avaliacao() {
		super();
	}

	public Avaliacao(Long id) {
		super(id);
	}

	public Avaliacao(Long id, String nota, String descricao, Usuario usuario) {
		super(id);
		this.nota = nota;
		this.descricao = descricao;
		this.usuario = usuario;
	}

	public String getNota() {
		return nota;
	}

	public void setNota(String nota) {
		this.nota = nota;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
