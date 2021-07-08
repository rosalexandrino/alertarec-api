package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "perfis")
@NamedQueries(value = { @NamedQuery(name = "Perfil.selecionarTodos", query = "SELECT p FROM Perfil p") })
public class Perfil extends Entidade {

	@NotBlank(message = "{perfil.perfil.vazio}")
	@Size(min = 1, max = 15, message = "{perfil.perfil.tamanho}")
	@Pattern(regexp = "^[A-Za-z]+$", message = "{perfil.perfil.invalido}")
	@Column(name = "perfil", length = 15, unique = true)
	private String perfil;

	public Perfil() {
		super();
	}

	public Perfil(Long id) {
		super(id);
	}

	public Perfil(Long id, String perfil) {
		super(id);
		this.perfil = perfil;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
}