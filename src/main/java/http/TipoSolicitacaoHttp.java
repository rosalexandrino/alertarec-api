package http;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TipoSolicitacaoHttp {

	private Long id;

	private String descricao;
	
	private Date dataCriacao;
	
	private Date dataAtualizacao;
	
	public TipoSolicitacaoHttp() {
	}
	
	public TipoSolicitacaoHttp(Long id, String descricao, Date dataCriacao, Date dataAtualizacao) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
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
	
	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}
}
