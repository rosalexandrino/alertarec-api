package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.TipoPontoDeRisco;

public class TipoPontoDeRiscoRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public TipoPontoDeRiscoRepository() {
		
		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(TipoPontoDeRisco tipo) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(tipo);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(TipoPontoDeRisco tipo) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(tipo);
		this.entityManager.getTransaction().commit();
	}

	public List<TipoPontoDeRisco> selecionarTodos() {
		TypedQuery<TipoPontoDeRisco> typedQuery = this.entityManager.createNamedQuery("TipoPontoDeRisco.selecionarTodos", TipoPontoDeRisco.class);
		return typedQuery.getResultList();
	}

	public TipoPontoDeRisco selecionarPorId(Long id) {
		return this.entityManager.find(TipoPontoDeRisco.class, id);
	}

	public void Excluir(Long id) {

		TipoPontoDeRisco tipo = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(tipo);
		this.entityManager.getTransaction().commit();

	}
}
