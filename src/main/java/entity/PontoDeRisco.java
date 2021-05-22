package entity;

import java.util.List;

import javax.persistence.CascadeType;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "pontos_risco")
@NamedQueries(value = { @NamedQuery(name = "PontoDeRisco.selecionarTodos", query = "SELECT p FROM PontoDeRisco p"),
		@NamedQuery(name = "PontoDeRisco.selecionarPorTipo", query = "SELECT p FROM PontoDeRisco p WHERE p.tipoDeRisco IN (SELECT t FROM TipoPontoDeRisco t WHERE t.id = :tipo)"),
		@NamedQuery(name = "PontoDeRisco.selecionarPorUsuario", query = "SELECT p FROM PontoDeRisco p WHERE p.usuario IN (SELECT u FROM Usuario u WHERE u.id = :id)") })
public class PontoDeRisco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "{pontoDeRisco.longitude.vazio}")
	@Column(name = "longitude")
	private double longitude;

	@NotBlank(message = "{pontoDeRisco.latitude.vazio}")
	@Column(name = "latitude")
	private double latitude;

	@Valid
	@NotBlank(message = "{pontoDeRisco.tipo.vazio}")
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_risco")
	private TipoPontoDeRisco tipoDeRisco;

	@Valid
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	@Valid
	@OneToMany(mappedBy = "ponto", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Solicitacao> solicitacoes;

	public PontoDeRisco() {
	}

	public PontoDeRisco(Long id, double longitude, double latitude, TipoPontoDeRisco tipoDeRisco, Usuario usuario,
			List<Solicitacao> solicitacoes) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.tipoDeRisco = tipoDeRisco;
		this.usuario = usuario;
		this.solicitacoes = solicitacoes;
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

	public TipoPontoDeRisco getTipoDeRisco() {
		return tipoDeRisco;
	}

	public void setTipoDeRisco(TipoPontoDeRisco tipoDeRisco) {
		this.tipoDeRisco = tipoDeRisco;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(List<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}
}
