package repository;

import java.util.ArrayList;
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
		
		try {
			TypedQuery<TipoPontoDeRisco> typedQuery = this.entityManager
					.createNamedQuery("TipoPontoDeRisco.selecionarTodos", TipoPontoDeRisco.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<TipoPontoDeRisco> tipos = new ArrayList<TipoPontoDeRisco>();
			return tipos;
		}
	}

	public TipoPontoDeRisco selecionarPorDescricao(String descricao) {
		
		try {
			TypedQuery<TipoPontoDeRisco> typedQuery = this.entityManager
					.createNamedQuery("TipoPontoDeRisco.selecionarPorDescricao", TipoPontoDeRisco.class);
			typedQuery.setParameter("descricao", descricao);
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public TipoPontoDeRisco selecionarPorId(Long id) {
		
		try {
			return this.entityManager.find(TipoPontoDeRisco.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public void Excluir(Long id) {

		TipoPontoDeRisco tipo = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(tipo);
		this.entityManager.getTransaction().commit();
	}
}
