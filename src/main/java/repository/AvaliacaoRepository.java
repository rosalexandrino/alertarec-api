package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Avaliacao;

public class AvaliacaoRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public AvaliacaoRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(Avaliacao avaliacao) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(avaliacao);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(Avaliacao avaliacao) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(avaliacao);
		this.entityManager.getTransaction().commit();
	}

	public List<Avaliacao> selecionarTodos() {

		try {
			TypedQuery<Avaliacao> typedQuery = this.entityManager.createNamedQuery("Avaliacao.selecionarTodos",
					Avaliacao.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
			return avaliacoes;
		}
	}

	public Avaliacao selecionarPorId(Long id) {

		try {
			return this.entityManager.find(Avaliacao.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public List<Avaliacao> selecionarPorUsuario(String usuarioEmail) {

		try {
			TypedQuery<Avaliacao> typedQuery = this.entityManager.createNamedQuery("Avaliacao.selecionarPorUsuario",
					Avaliacao.class);
			typedQuery.setParameter("email", usuarioEmail);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
			return avaliacoes;
		}
	}

	public void Excluir(Long id) {

		Avaliacao avaliacao = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(avaliacao);
		this.entityManager.getTransaction().commit();
	}
}
