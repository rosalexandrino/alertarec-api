package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TipoPontoDeApoioHttp {

	private Long id;

	private String descricao;
	
	public TipoPontoDeApoioHttp() {
	}
	
	public TipoPontoDeApoioHttp(Long id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
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
}
