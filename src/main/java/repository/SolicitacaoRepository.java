package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Solicitacao;

public class SolicitacaoRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public SolicitacaoRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(Solicitacao solicitacao) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(solicitacao);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(Solicitacao solicitacao) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(solicitacao);
		this.entityManager.getTransaction().commit();
	}

	public List<Solicitacao> selecionarTodos() {
		
		try {
			TypedQuery<Solicitacao> typedQuery = this.entityManager.createNamedQuery("Solicitacao.selecionarTodos",
					Solicitacao.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
			return solicitacoes;
		}
	}

	public Solicitacao selecionarPorId(Long id) {
		
		try {
			return this.entityManager.find(Solicitacao.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Solicitacao> selecionarPorTipo(Long tipo) {
		
		try {
			TypedQuery<Solicitacao> typedQuery = this.entityManager.createNamedQuery("Solicitacao.selecionarPorTipo",
					Solicitacao.class);
			typedQuery.setParameter("tipo", tipo);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
			return solicitacoes;
		}
	}

	public List<Solicitacao> selecionarPorUsuario(String usuarioEmail) {
		
		try {
			TypedQuery<Solicitacao> typedQuery = this.entityManager.createNamedQuery("Solicitacao.selecionarPorUsuario",
					Solicitacao.class);
			typedQuery.setParameter("email", usuarioEmail);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
			return solicitacoes;
		}
	}

	public List<Solicitacao> selecionarAbertas() {
		
		try {
			TypedQuery<Solicitacao> typedQuery = this.entityManager.createNamedQuery("Solicitacao.selecionarAbertas",
					Solicitacao.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
			return solicitacoes;
		}
	}

	public void Excluir(Long id) {

		Solicitacao solicitacao = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(solicitacao);
		this.entityManager.getTransaction().commit();

	}

}
