package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "avaliacoes")
@NamedQueries(value = { @NamedQuery(name = "Avaliacao.selecionarTodos", query = "SELECT a FROM Avaliacao a") })
public class Avaliacao extends Entidade{
	
	@Pattern(regexp = "[1-5]{1}", message = "{avaliacao.nota.invalido}")
	@NotBlank(message = "{avaliacao.nota.vazio}")
	@Column(name = "nota")
	private String nota;
	
	@NotBlank(message = "{avaliacao.descricao.vazio}")
	@Column(name = "descricao")
	private String descricao;

	public Avaliacao() {
		super();
	}

	public Avaliacao(Long id) {
		super(id);
	}

	public Avaliacao(Long id, String nota, String descricao) {
		super(id);
		this.nota = nota;
		this.descricao = descricao;
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
	
}
