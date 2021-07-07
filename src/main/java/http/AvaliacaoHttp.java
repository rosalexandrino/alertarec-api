package http;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvaliacaoHttp {

	private Long id;
	
	private String nota;

	private String descricao;
	
	private String usuarioEmail;
	
	private Date dataCriacao;
	
	private Date dataAtualizacao;

	public AvaliacaoHttp() {
	}
	
	public AvaliacaoHttp(Long id, String nota, String descricao, String usuarioEmail, Date dataCriacao, Date dataAtualizacao) {
		super();
		this.id = id;
		this.nota = nota;
		this.descricao = descricao;
		this.usuarioEmail = usuarioEmail;
		this.dataCriacao = dataCriacao;
		this.dataAtualizacao = dataAtualizacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public String getUsuarioEmail() {
		return usuarioEmail;
	}

	public void setUsuario(String usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
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
