package repository;

import java.util.ArrayList;
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
		
		try {
			TypedQuery<TipoPontoDeApoio> typedQuery = this.entityManager
					.createNamedQuery("TipoPontoDeApoio.selecionarTodos", TipoPontoDeApoio.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<TipoPontoDeApoio> tipos = new ArrayList<TipoPontoDeApoio>();
			return tipos;
		}
	}

	public TipoPontoDeApoio selecionarPorDescricao(String descricao) {
		
		try {
			TypedQuery<TipoPontoDeApoio> typedQuery = this.entityManager
					.createNamedQuery("TipoPontoDeApoio.selecionarPorDescricao", TipoPontoDeApoio.class);
			typedQuery.setParameter("descricao", descricao);
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public TipoPontoDeApoio selecionarPorId(Long id) {
		
		try {
			return this.entityManager.find(TipoPontoDeApoio.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public void Excluir(Long id) {

		TipoPontoDeApoio tipo = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(tipo);
		this.entityManager.getTransaction().commit();

	}
}
