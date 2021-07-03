package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tipos_ponto_apoio")
@NamedQueries(value = { @NamedQuery(name = "TipoPontoDeApoio.selecionarTodos", query = "SELECT t FROM TipoPontoDeApoio t") })
public class TipoPontoDeApoio extends Entidade{
	
	@NotBlank(message = "{tipoPontoDeApoio.descricao.vazio}")
	@Column(name = "descricao")
	private String descricao;

	public TipoPontoDeApoio() {
		super();
	}

	public TipoPontoDeApoio(Long id) {
		super(id);
	}

	public TipoPontoDeApoio(Long id, String descricao) {
		super(id);
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
