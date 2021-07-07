package repository;

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
		TypedQuery<Solicitacao> typedQuery = this.entityManager.createNamedQuery("Solicitacao.selecionarTodos", Solicitacao.class);
		return typedQuery.getResultList();
	}

	public Solicitacao selecionarPorId(Long id) {
		return this.entityManager.find(Solicitacao.class, id);
	}
	
	public List<Solicitacao> selecionarPorTipo(Long tipo) {
		TypedQuery<Solicitacao> typedQuery = this.entityManager.createNamedQuery("Solicitacao.selecionarPorTipo", Solicitacao.class);
		typedQuery.setParameter("tipo", tipo);
		return typedQuery.getResultList();
	}
	
	public List<Solicitacao> selecionarPorUsuario(String usuarioEmail) {
		TypedQuery<Solicitacao> typedQuery = this.entityManager.createNamedQuery("Solicitacao.selecionarPorUsuario", Solicitacao.class);
		typedQuery.setParameter("email", usuarioEmail);
		return typedQuery.getResultList();
	}
	
	public List<Solicitacao> selecionarAbertas() {
		TypedQuery<Solicitacao> typedQuery = this.entityManager.createNamedQuery("Solicitacao.selecionarAbertas", Solicitacao.class);
		return typedQuery.getResultList();
	}

	public void Excluir(Long id) {

		Solicitacao solicitacao = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(solicitacao);
		this.entityManager.getTransaction().commit();

	}

}
