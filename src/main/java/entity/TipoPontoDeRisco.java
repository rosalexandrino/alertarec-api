package entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "tipos_ponto_risco")
@NamedQueries(value = {
		@NamedQuery(name = "TipoPontoDeRisco.selecionarTodos", query = "SELECT t FROM TipoPontoDeRisco t") })
public class TipoPontoDeRisco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "{tipoPontoDeRisco.descricao.vazio}")
	@Column(name = "descricao")
	private String descricao;

	@Valid
	@OneToMany(mappedBy = "tipoDeRisco", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PontoDeRisco> pontosDeRisco;

	public TipoPontoDeRisco() {
		super();
		this.pontosDeRisco = new ArrayList<>();
	}

	public TipoPontoDeRisco(Long id, String descricao, List<PontoDeRisco> pontosDeRisco) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.pontosDeRisco = pontosDeRisco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<PontoDeRisco> getPontosDeRisco() {
		return pontosDeRisco;
	}

	public void setPontosDeRisco(List<PontoDeRisco> pontosDeRisco) {
		this.pontosDeRisco = pontosDeRisco;
	}
}
