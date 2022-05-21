package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Perfil;

public class PerfilRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public PerfilRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(Perfil perfil) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(perfil);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(Perfil perfil) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(perfil);
		this.entityManager.getTransaction().commit();
	}

	public List<Perfil> selecionarTodos() {

		try {
			TypedQuery<Perfil> typedQuery = this.entityManager.createNamedQuery("Perfil.selecionarTodos", Perfil.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Perfil> perfis = new ArrayList<Perfil>();
			return perfis;
		}
	}

	public Perfil selecionarPorId(Long id) {

		try {
			return this.entityManager.find(Perfil.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public Perfil selecionarPorPerfil(String perfil) {

		try {
			TypedQuery<Perfil> typedQuery = this.entityManager.createNamedQuery("Perfil.selecionarPorPerfil",
					Perfil.class);
			typedQuery.setParameter("perfil", perfil);
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public void Excluir(Long id) {

		Perfil perfil = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(perfil);
		this.entityManager.getTransaction().commit();

	}
}
