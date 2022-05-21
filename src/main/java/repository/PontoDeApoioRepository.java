package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.PontoDeApoio;

public class PontoDeApoioRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public PontoDeApoioRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(PontoDeApoio ponto) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(ponto);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(PontoDeApoio ponto) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(ponto);
		this.entityManager.getTransaction().commit();
	}

	public List<PontoDeApoio> selecionarTodos() {

		try {
			TypedQuery<PontoDeApoio> typedQuery = this.entityManager.createNamedQuery("PontoDeApoio.selecionarTodos",
					PontoDeApoio.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<PontoDeApoio> pontos = new ArrayList<PontoDeApoio>();
			return pontos;
		}
	}

	public PontoDeApoio selecionarPorId(Long id) {

		try {
			return this.entityManager.find(PontoDeApoio.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public List<PontoDeApoio> selecionarPorTipo(Long tipo) {

		try {
			TypedQuery<PontoDeApoio> typedQuery = this.entityManager.createNamedQuery("PontoDeApoio.selecionarPorTipo",
					PontoDeApoio.class);
			typedQuery.setParameter("tipo", tipo);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<PontoDeApoio> pontos = new ArrayList<PontoDeApoio>();
			return pontos;
		}
	}

	public List<PontoDeApoio> selecionarPorUsuario(String usuarioEmail) {

		try {
			TypedQuery<PontoDeApoio> typedQuery = this.entityManager
					.createNamedQuery("PontoDeApoio.selecionarPorUsuario", PontoDeApoio.class);
			typedQuery.setParameter("email", usuarioEmail);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<PontoDeApoio> pontos = new ArrayList<PontoDeApoio>();
			return pontos;
		}
	}

	public void Excluir(Long id) {

		PontoDeApoio ponto = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(ponto);
		this.entityManager.getTransaction().commit();
	}

}
