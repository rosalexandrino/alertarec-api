package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Usuario;

public class UsuarioRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public UsuarioRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(Usuario usuario) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(usuario);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(Usuario usuario) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(usuario);
		this.entityManager.getTransaction().commit();
	}

	public List<Usuario> selecionarTodos() {

		try {
			TypedQuery<Usuario> typedQuery = this.entityManager.createNamedQuery("Usuario.selecionarTodos",
					Usuario.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Usuario> usuarios = new ArrayList<Usuario>();
			return usuarios;
		}
	}

	public Usuario selecionarPorEmail(String email) {

		try {
			TypedQuery<Usuario> typedQuery = this.entityManager.createNamedQuery("Usuario.selecionarPorEmail",
					Usuario.class);
			typedQuery.setParameter("email", email);
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public Usuario selecionarPorId(Long id) {

		try {
			return this.entityManager.find(Usuario.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public void Excluir(Long id) {

		Usuario usuario = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(usuario);
		this.entityManager.getTransaction().commit();
	}
}