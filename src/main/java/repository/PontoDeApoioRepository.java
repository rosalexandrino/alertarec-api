package repository;

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
		TypedQuery<PontoDeApoio> typedQuery = this.entityManager.createNamedQuery("PontoDeApoio.selecionarTodos", PontoDeApoio.class);
		return typedQuery.getResultList();
	}

	public PontoDeApoio selecionarPorId(Long id) {
		return this.entityManager.find(PontoDeApoio.class, id);
	}

	public void Excluir(Long id) {

		PontoDeApoio ponto = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(ponto);
		this.entityManager.getTransaction().commit();

	}

}
