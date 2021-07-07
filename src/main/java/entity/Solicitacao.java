package entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "solicitacoes")
@NamedQueries(value = { @NamedQuery(name = "Solicitacao.selecionarTodos", query = "SELECT s FROM Solicitacao s"),
		@NamedQuery(name = "Solicitacao.selecionarPorTipo", query = "SELECT s FROM Solicitacao s WHERE s.tipo IN (SELECT t FROM TipoSolicitacao t WHERE t.id = :tipo)"),
		@NamedQuery(name = "Solicitacao.selecionarPorUsuario", query = "SELECT s FROM Solicitacao s WHERE s.usuario IN (SELECT u FROM Usuario u WHERE u.email = :email)"),
		@NamedQuery(name = "Solicitacao.selecionarAbertas", query = "SELECT s FROM Solicitacao s WHERE s.dataConclusao IS NULL") })
public class Solicitacao extends Entidade{

	@NotBlank(message = "{solicitacao.descricao.vazio}")
	@Size(min = 1, max = 220, message = "{solicitacao.descricao.tamanho}")
	@Pattern(regexp = "^[A-Za-z0-9áàâãéèêíïóôõöúçñÁÀÂÃÉÈÊÍÏÓÔÕÖÚÇÑ\\s]+$", message = "{solicitacao.descricao.invalido}")
	@Column(name = "descricao", length = 220)
	private String descricao;
	
	@Valid
	@NotNull(message = "{solicitacao.tipo.vazio}")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "tipo")
	private TipoSolicitacao tipo;
	
	@Valid
	@NotNull(message = "{solicitacao.ponto.vazio}")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "ponto")
	private PontoDeRisco ponto;
	
	@Valid
	@NotNull(message = "{solicitacao.usuario.vazio}")
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	@NotNull(message = "{solicitacao.data.vazio}")
	@Temporal(value = TemporalType.DATE)
	@Past(message = "{solicitacao.data.invalida}")
	@Column(name = "data_solicitacao")
	private Date dataSolicitacao;

	@Temporal(value = TemporalType.DATE)
	@Past(message = "{solicitacao.data.invalida}")
	@Column(name = "data_conclusao")
	private Date dataConclusao;

	public Solicitacao() {
		super();
	}

	public Solicitacao(Long id) {
		super(id);
	}

	public Solicitacao(Long id, String descricao, TipoSolicitacao tipo, PontoDeRisco ponto, Usuario usuario, Date dataSolicitacao, Date dataConclusao) {
		super(id);
		this.descricao = descricao;
		this.tipo = tipo;
		this.ponto = ponto;
		this.usuario = usuario;
		this.dataSolicitacao = dataSolicitacao;
		this.dataConclusao = dataConclusao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoSolicitacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoSolicitacao tipo) {
		this.tipo = tipo;
	}

	public PontoDeRisco getPonto() {
		return ponto;
	}

	public void setPonto(PontoDeRisco ponto) {
		this.ponto = ponto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public Date getDataConclusao() {
		return dataConclusao;
	}

	public void setDataConclusao(Date dataConclusao) {
		this.dataConclusao = dataConclusao;
	}
	
}
