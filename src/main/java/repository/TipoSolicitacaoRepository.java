package repository;

import java.util.ArrayList;
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
		
		try {
			TypedQuery<TipoSolicitacao> typedQuery = this.entityManager
					.createNamedQuery("TipoSolicitacao.selecionarTodos", TipoSolicitacao.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<TipoSolicitacao> tipos = new ArrayList<TipoSolicitacao>();
			return tipos;
		}
	}

	public TipoSolicitacao selecionarPorDescricao(String descricao) {
		
		try {
			TypedQuery<TipoSolicitacao> typedQuery = this.entityManager
					.createNamedQuery("TipoSolicitacao.selecionarPorDescricao", TipoSolicitacao.class);
			typedQuery.setParameter("descricao", descricao);
			return typedQuery.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	public TipoSolicitacao selecionarPorId(Long id) {
		
		try {
			return this.entityManager.find(TipoSolicitacao.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public void Excluir(Long id) {

		TipoSolicitacao tipo = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(tipo);
		this.entityManager.getTransaction().commit();
	}
}
