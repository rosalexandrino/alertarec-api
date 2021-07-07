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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tipos_ponto_risco")
@NamedQueries(value = {
		@NamedQuery(name = "TipoPontoDeRisco.selecionarTodos", query = "SELECT t FROM TipoPontoDeRisco t") })
public class TipoPontoDeRisco extends Entidade{

	@NotBlank(message = "{tipoPontoDeRisco.descricao.vazio}")
	@Pattern(regexp = "^[A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ\\s]+$", message = "{tipoPontoDeRisco.descricao.invalido}")
	@Size(max = 50, message = "{tipoPontoDeRisco.descricao.tamanho}")
	@Column(name = "descricao")
	private String descricao;

	@Valid
	@OneToMany(mappedBy = "tipoDeRisco", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PontoDeRisco> pontosDeRisco;

	public TipoPontoDeRisco() {
		super();
		this.pontosDeRisco = new ArrayList<>();
	}
	
	public TipoPontoDeRisco(Long id) {
		super(id);
		this.pontosDeRisco = new ArrayList<>();
	}

	public TipoPontoDeRisco(Long id, String descricao, List<PontoDeRisco> pontosDeRisco) {
		super(id);
		this.descricao = descricao;
		this.pontosDeRisco = pontosDeRisco;
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
