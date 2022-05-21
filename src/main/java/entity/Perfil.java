package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "perfis")
@NamedQueries(value = { @NamedQuery(name = "Perfil.selecionarTodos", query = "SELECT p FROM Perfil p"),
		@NamedQuery(name = "Perfil.selecionarPorPerfil", query = "SELECT p FROM Perfil p WHERE p.perfil = :perfil")})
public class Perfil extends Entidade {

	@NotBlank(message = "{perfil.perfil.vazio}")
	@Size(min = 1, max = 15, message = "{perfil.perfil.tamanho}")
	@Pattern(regexp = "^[A-Za-z]+$", message = "{perfil.perfil.invalido}")
	@Column(name = "perfil", length = 15, unique = true)
	private String perfil;
	
    @ManyToMany(mappedBy = "perfis")
    private Set<Usuario> usuarios;

	public Perfil() {
		super();
		this.usuarios = new HashSet<>();
	}

	public Perfil(Long id) {
		super(id);
		this.usuarios = new HashSet<>();
	}

	public Perfil(Long id, String perfil, Set<Usuario> usuarios) {
		super(id);
		this.perfil = perfil;
		this.usuarios = usuarios;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}
	
	public Set<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}