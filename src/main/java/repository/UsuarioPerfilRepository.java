package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.UsuarioPerfil;

public class UsuarioPerfilRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public UsuarioPerfilRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(UsuarioPerfil usuarioPerfil) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(usuarioPerfil);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(UsuarioPerfil usuarioPerfil) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(usuarioPerfil);
		this.entityManager.getTransaction().commit();
	}

	public List<UsuarioPerfil> selecionarTodos() {

		try {
			TypedQuery<UsuarioPerfil> typedQuery = this.entityManager.createNamedQuery("UsuarioPerfil.selecionarTodos",
					UsuarioPerfil.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<UsuarioPerfil> usuariosPerfil = new ArrayList<UsuarioPerfil>();
			return usuariosPerfil;
		}
	}

	public List<UsuarioPerfil> selecionarPorEmail(String email) {

		try {
			TypedQuery<UsuarioPerfil> typedQuery = this.entityManager
					.createNamedQuery("UsuarioPerfil.selecionarPorEmail", UsuarioPerfil.class);
			typedQuery.setParameter("email", email);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<UsuarioPerfil> usuariosPerfil = new ArrayList<UsuarioPerfil>();
			return usuariosPerfil;
		}
	}

	public UsuarioPerfil selecionarPorEmailPerfil(String email, String perfil) {

		try {
			TypedQuery<UsuarioPerfil> typedQuery = this.entityManager
					.createNamedQuery("UsuarioPerfil.selecionarPorEmailPerfil", UsuarioPerfil.class);
			typedQuery.setParameter("email", email);
			typedQuery.setParameter("perfil", perfil);
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public List<UsuarioPerfil> selecionarPorPerfil(String perfil) {

		try {
			TypedQuery<UsuarioPerfil> typedQuery = this.entityManager
					.createNamedQuery("UsuarioPerfil.selecionarPorPerfil", UsuarioPerfil.class);
			typedQuery.setParameter("perfil", perfil);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<UsuarioPerfil> usuariosPerfil = new ArrayList<UsuarioPerfil>();
			return usuariosPerfil;
		}
	}

	public UsuarioPerfil selecionarPorId(Long id) {
		
		try {
			return this.entityManager.find(UsuarioPerfil.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public void Excluir(Long id) {

		UsuarioPerfil perfil = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(perfil);
		this.entityManager.getTransaction().commit();

	}
}
