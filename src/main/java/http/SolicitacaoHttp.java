package http;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SolicitacaoHttp {

	private Long id;
	
	private String descricao;
	
	private Long tipo;
	
	private Long ponto;
	
	private String usuarioEmail;
	
	private Date dataSolicitacao;
	
	private Date dataConclusao;
	
	public SolicitacaoHttp() {
	}

	public SolicitacaoHttp(Long id, String descricao, Long tipo, Long ponto, String usuarioEmail, Date dataSolicitacao,
			Date dataConclusao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.tipo = tipo;
		this.ponto = ponto;
		this.usuarioEmail = usuarioEmail;
		this.dataSolicitacao = dataSolicitacao;
		this.dataConclusao = dataConclusao;
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

	public Long getTipo() {
		return tipo;
	}

	public void setTipo(Long tipo) {
		this.tipo = tipo;
	}

	public Long getPonto() {
		return ponto;
	}

	public void setPonto(Long ponto) {
		this.ponto = ponto;
	}

	public String getUsuarioEmail() {
		return usuarioEmail;
	}

	public void setUsuario(String usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
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
