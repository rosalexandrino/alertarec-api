package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "pontos_apoio")
@NamedQueries(value = { @NamedQuery(name = "PontoDeApoio.selecionarTodos", query = "SELECT p FROM PontoDeApoio p"),
		@NamedQuery(name = "PontoDeApoio.selecionarPorTipo", query = "SELECT p FROM PontoDeApoio p WHERE p.tipoDeApoio IN (SELECT t FROM TipoPontoDeApoio t WHERE t.id = :tipo)"),
		@NamedQuery(name = "PontoDeApoio.selecionarPorUsuario", query = "SELECT p FROM PontoDeApoio p WHERE p.usuario IN (SELECT u FROM Usuario u WHERE u.id = :id)") })
public class PontoDeApoio{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "{pontoDeApoio.longitude.vazio}")
	@Column(name = "longitude")
	private double longitude;

	@NotBlank(message = "{pontoDeApoio.latitude.vazio}")
	@Column(name = "latitude")
	private double latitude;

	@Valid
	@NotBlank(message = "{pontoDeApoio.tipo.vazio}")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_apoio")
	private TipoPontoDeApoio tipoDeApoio;

	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario")
	private Usuario usuario;

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

	public TipoPontoDeApoio getTipoDeApoio() {
		return tipoDeApoio;
	}

	public void setTipoDeApoio(TipoPontoDeApoio tipoDeApoio) {
		this.tipoDeApoio = tipoDeApoio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}