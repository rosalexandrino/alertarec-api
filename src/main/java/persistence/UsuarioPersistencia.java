package persistence;


import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import model.Usuario;

@Stateless
public class UsuarioPersistencia extends EntidadePersistencia<Usuario> {

	@Override
	public void salvar(Usuario usuario) throws PersistenceException {
		if (selecionaPorEmail(usuario.getEmail()) != null) {
			throw new PersistenceException("Usuário inválido");
		}
		this.entityManager.persist(usuario);
	}

	@Override
	public void remover(Usuario usuario) {
		if (!this.entityManager.contains(usuario)) {
			usuario = this.entityManager.merge(usuario);
		}
		this.entityManager.remove(usuario);
		this.entityManager.flush();
	}

	@Override
	public void atualizar(Usuario usuario) {
		this.entityManager.merge(usuario);
		this.entityManager.flush();
	}

	@Override
	public Usuario selecionaPorId(Long id) {
		return this.entityManager.find(Usuario.class, id);
	}

	@Override
	public List<Usuario> selecionaTodos() {
		TypedQuery<Usuario> typedQuery = this.entityManager.createNamedQuery("Usuario.selecionaTodos", Usuario.class);
		return typedQuery.getResultList();
	}

	public Usuario selecionaPorEmail(String email) {
		TypedQuery<Usuario> typedQuery = this.entityManager.createNamedQuery("Usuario.selecionaPorEmail", Usuario.class);
		typedQuery.setParameter("email", email);
		try {
			return typedQuery.getSingleResult();
		} catch (Exception ex) {
			return null;
		}
	}
	
}
