package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public abstract class Entidade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "criado_em")
	private LocalDateTime dataCriacao;

	@Column(name = "atualizado_em")
	private LocalDateTime dataAtualizacao;

	public Entidade() {
	}

	public Entidade(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}


	public LocalDateTime getDataAtualizacao() {
		return dataAtualizacao;
	}
	
	@PrePersist
	private void onCreate() {
		this.dataCriacao = LocalDateTime.now();
		this.dataAtualizacao = LocalDateTime.now();
	}

	@PreUpdate
	private void onUpdate() {
		this.dataAtualizacao = LocalDateTime.now();
	}

}