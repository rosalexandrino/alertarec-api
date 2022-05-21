package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioPerfilHttp {
	
	private String email;
	
	private String perfil;
	
	public UsuarioPerfilHttp() {
		super();
	}

	public UsuarioPerfilHttp(String email, String perfil) {
		super();
		this.email = email;
		this.perfil = perfil;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
}