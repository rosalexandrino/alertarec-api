package http;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UsuarioPerfilHttp {

	private String email;

	private Set<String> perfis;

	public UsuarioPerfilHttp() {
	}

	public UsuarioPerfilHttp(String email, Set<String> perfis) {
		super();
		this.email = email;
		this.perfis = perfis;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<String> perfis) {
		this.perfis = perfis;
	}

}