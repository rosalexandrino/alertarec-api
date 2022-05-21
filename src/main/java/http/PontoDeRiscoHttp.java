package http;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PontoDeRiscoHttp {

	private Long id;
	
	private double longitude;
	
	private double latitude;
	
	private Long tipoDeRisco;
	
	private String usuarioEmail;
	
	public PontoDeRiscoHttp() {
	}

	public PontoDeRiscoHttp(Long id, double longitude, double latitude, Long tipoDeRisco, String usuarioEmail) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.tipoDeRisco = tipoDeRisco;
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

	public Long getTipoDeRisco() {
		return tipoDeRisco;
	}

	public void setTipoDeRisco(Long tipoDeRisco) {
		this.tipoDeRisco = tipoDeRisco;
	}

	public String getUsuarioEmail() {
		return usuarioEmail;
	}

	public void setUsuarioEmail(String usuarioEmail) {
		this.usuarioEmail = usuarioEmail;
	}
}
