package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PerfilHttp {
		
	Long id;
	
	private String perfil;
	
	public PerfilHttp() {
		super();
	}

	public PerfilHttp(Long id, String perfil) {
		super();
		this.id = id;
		this.perfil = perfil;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
}
