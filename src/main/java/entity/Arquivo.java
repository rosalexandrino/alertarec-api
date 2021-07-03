package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "arquivos")
@NamedQueries(value = { @NamedQuery(name = "Arquivo.selecionarTodos", query = "SELECT a FROM Arquivo a")})
public class Arquivo extends Entidade{
	
	@NotBlank(message = "{arquivo.nome.vazio}")
	@Column(name = "nome")
	private String nome;
	
	@NotBlank(message = "{arquivo.arquivo.vazio}")
	@Column(name = "arquivo", length = 1000000000)
	private byte[] arquivo;

	public Arquivo() {
		super();
	}

	public Arquivo(Long id) {
		super(id);
	}

	public Arquivo(Long id, String nome, byte[] arquivo) {
		super(id);
		this.nome = nome;
		this.arquivo = arquivo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}
	
}