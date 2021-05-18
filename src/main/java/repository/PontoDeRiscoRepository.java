package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.PontoDeRisco;

public class PontoDeRiscoRepository {
	
	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public PontoDeRiscoRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(PontoDeRisco ponto) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(ponto);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(PontoDeRisco ponto) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(ponto);
		this.entityManager.getTransaction().commit();
	}

	public List<PontoDeRisco> selecionarTodos() {
		TypedQuery<PontoDeRisco> typedQuery = this.entityManager.createNamedQuery("PontoDeRisco.selecionarTodos", PontoDeRisco.class);
		return typedQuery.getResultList();
	}

	public PontoDeRisco selecionarPorId(Long id) {
		return this.entityManager.find(PontoDeRisco.class, id);
	}

	public void Excluir(Long id) {

		PontoDeRisco ponto = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(ponto);
		this.entityManager.getTransaction().commit();

	}

}
