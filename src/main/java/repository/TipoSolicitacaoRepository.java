package repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.TipoSolicitacao;

public class TipoSolicitacaoRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public TipoSolicitacaoRepository() {
		
		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(TipoSolicitacao tipo) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(tipo);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(TipoSolicitacao tipo) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(tipo);
		this.entityManager.getTransaction().commit();
	}

	public List<TipoSolicitacao> selecionarTodos() {
		TypedQuery<TipoSolicitacao> typedQuery = this.entityManager.createNamedQuery("TipoSolicitacao.selecionarTodos", TipoSolicitacao.class);
		return typedQuery.getResultList();
	}

	public TipoSolicitacao selecionarPorId(Long id) {
		return this.entityManager.find(TipoSolicitacao.class, id);
	}

	public void Excluir(Long id) {

		TipoSolicitacao tipo = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(tipo);
		this.entityManager.getTransaction().commit();

	}
}
