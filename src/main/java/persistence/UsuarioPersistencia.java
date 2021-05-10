package persistence;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import model.Usuario;

public class UsuarioPersistencia extends EntidadePersistencia<Usuario> {

	private final EntityManagerFactory entityManagerFactory;
	 
	private final EntityManager entityManager;
	
	public UsuarioPersistencia(){
		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}
	
	@Override
	public void salvar(Usuario usuario) throws PersistenceException {
		if (selecionaPorEmail(usuario.getEmail()) != null) {
			throw new PersistenceException("Usuário inválido");
		}
		entityManager.persist(usuario);
	}

	@Override
	public void remover(Usuario usuario) {
		if (!this.entityManager.contains(usuario)) {
			usuario = this.entityManager.merge(usuario);
		}
		entityManager.remove(usuario);
		entityManager.flush();
	}

	@Override
	public void atualizar(Usuario usuario) {
		entityManager.merge(usuario);
		entityManager.flush();
	}

	@Override
	public Usuario selecionaPorId(Long id) {
		return entityManager.find(Usuario.class, id);
	}

	@Override
	public List<Usuario> selecionaTodos() {
		TypedQuery<Usuario> typedQuery = entityManager.createNamedQuery("Usuario.selecionaTodos", Usuario.class);
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
