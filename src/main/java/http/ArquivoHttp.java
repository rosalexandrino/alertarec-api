package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ArquivoHttp {
	
	private Long id;
	
	private String nome;
	
	public ArquivoHttp() {
	}

	public ArquivoHttp(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
