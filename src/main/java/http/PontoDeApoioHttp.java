package http;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PontoDeApoioHttp {

	private Long id;
	
	private double longitude;
	
	private double latitude;
	
	private Long tipoDeApoio;
	
	private Long usuario;

	private Date dataCriacao;
	
	private Date dataAtualizacao;
	
	public PontoDeApoioHttp() {
	}

	public PontoDeApoioHttp(Long id, double longitude, double latitude, Long tipoDeApoio, Long usuario, Date dataCriacao, Date dataAtualizacao) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.tipoDeApoio = tipoDeApoio;
		this.usuario = usuario;
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

	public Long getTipoDeApoio() {
		return tipoDeApoio;
	}

	public void setTipoDeApoio(Long tipoDeApoio) {
		this.tipoDeApoio = tipoDeApoio;
	}

	public Long getUsuario() {
		return usuario;
	}

	public void setUsuario(Long usuario) {
		this.usuario = usuario;
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
