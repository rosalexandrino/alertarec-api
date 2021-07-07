package http;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PontoDeRiscoHttp {

	private Long id;
	
	private double longitude;
	
	private double latitude;
	
	private Long tipoDeRisco;
	
	private String usuarioEmail;
	
	private Date dataCriacao;
	
	private Date dataAtualizacao;
	
	public PontoDeRiscoHttp() {
	}

	public PontoDeRiscoHttp(Long id, double longitude, double latitude, Long tipoDeRisco, String usuarioEmail, Date dataCriacao, Date dataAtualizacao) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.tipoDeRisco = tipoDeRisco;
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
