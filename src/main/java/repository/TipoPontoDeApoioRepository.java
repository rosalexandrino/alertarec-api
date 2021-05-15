package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.TipoPontoDeApoio;

public class TipoPontoDeApoioRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public TipoPontoDeApoioRepository() {
		
		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(TipoPontoDeApoio tipo) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(tipo);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(TipoPontoDeApoio tipo) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(tipo);
		this.entityManager.getTransaction().commit();
	}

	public List<TipoPontoDeApoio> selecionarTodos() {
		TypedQuery<TipoPontoDeApoio> typedQuery = this.entityManager.createNamedQuery("TipoPontoDeApoio.selecionarTodos", TipoPontoDeApoio.class);
		return typedQuery.getResultList();
	}

	public TipoPontoDeApoio selecionarPorId(Long id) {
		return this.entityManager.find(TipoPontoDeApoio.class, id);
	}

	public void Excluir(Long id) {

		TipoPontoDeApoio tipo = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(tipo);
		this.entityManager.getTransaction().commit();

	}
}
