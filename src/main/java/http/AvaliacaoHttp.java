package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AvaliacaoHttp {

	private Long id;
	
	private String nota;

	private String descricao;

	public AvaliacaoHttp() {
	}
	
	public AvaliacaoHttp(Long id, String nota, String descricao) {
		super();
		this.id = id;
		this.nota = nota;
		this.descricao = descricao;
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
}
