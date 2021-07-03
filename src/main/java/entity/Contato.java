package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "contatos")
@NamedQueries(value = { @NamedQuery(name = "Contato.selecionarTodos", query = "SELECT c FROM Contato c") })
public class Contato extends Entidade{
	
	@NotBlank(message = "{contato.descricao.vazio}")
	@Column(name = "descricao")
	private String descricao;

	@Pattern(regexp = "[0-9]+$", message = "{contato.telefone.invalido}")
	@NotBlank(message = "{contato.telefone.vazio}")
	@Column(name = "telefone")
	private String telefone;

	public Contato() {
		super();
	}

	public Contato(Long id) {
		super(id);
	}
	

	public Contato(Long id, String descricao, String telefone) {
		super(id);
		this.descricao = descricao;
		this.telefone = telefone;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	
}
