package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PontoDeApoioHttp {

	private Long id;
	
	private double longitude;
	
	private double latitude;
	
	private Long tipoDeApoio;
	
	private String usuarioEmail;
	
	public PontoDeApoioHttp() {
	}

	public PontoDeApoioHttp(Long id, double longitude, double latitude, Long tipoDeApoio, String usuarioEmail) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.tipoDeApoio = tipoDeApoio;
		this.usuarioEmail = usuarioEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public Long getTipoDeApoio() {
		return tipoDeApoio;
	}

	public void setTipoDeApoio(Long tipoDeApoio) {
		this.tipoDeApoio = tipoDeApoio;
	}

	public String getUsuarioEmail() {
		return usuarioEmail;
	}

	public void setUsuarioEmail(String usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
	}
}
