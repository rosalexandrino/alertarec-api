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

@Entity
@Table(name = "tipos_solicitacao")
@NamedQueries(value = { @NamedQuery(name = "TipoSolicitacao.selecionarTodos", query = "SELECT t FROM TipoSolicitacao t") })
public class TipoSolicitacao extends Entidade{
	
	@NotBlank(message = "{tipoSolicitacao.descricao.vazio}")
	@Column(name = "descricao")
	private String descricao;
	
	@Valid
	@OneToMany(mappedBy = "tipo", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Solicitacao> solicitacoes;

	public TipoSolicitacao() {
		super();
		this.solicitacoes = new ArrayList<>();
	}

	public TipoSolicitacao(Long id) {
		super(id);
		this.solicitacoes = new ArrayList<>();
	}

	public TipoSolicitacao(Long id, String descricao, List<Solicitacao> solicitacoes) {
		super(id);
		this.descricao = descricao;
		this.solicitacoes = solicitacoes;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
}
