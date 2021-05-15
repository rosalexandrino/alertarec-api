package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContatoHttp {

	private Long id;

	private String descricao;

	private String telefone;

	public ContatoHttp() {
	}

	public ContatoHttp(Long id, String descricao, String telefone) {
		super();
		this.id = id;
		this.descricao = descricao;
		this.telefone = telefone;
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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
}
