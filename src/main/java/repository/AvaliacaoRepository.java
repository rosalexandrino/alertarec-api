package repository;

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
		TypedQuery<Avaliacao> typedQuery = this.entityManager.createNamedQuery("Avaliacao.selecionarTodos",
				Avaliacao.class);
		return typedQuery.getResultList();
	}

	public Avaliacao selecionarPorId(Long id) {
		return this.entityManager.find(Avaliacao.class, id);
	}

	public List<Avaliacao> selecionarPorUsuario(String usuarioEmail) {
		TypedQuery<Avaliacao> typedQuery = this.entityManager.createNamedQuery("Avaliacao.selecionarPorUsuario", Avaliacao.class);
		typedQuery.setParameter("email", usuarioEmail);
		return typedQuery.getResultList();
	}
	
	public void Excluir(Long id) {

		Avaliacao avaliacao = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(avaliacao);
		this.entityManager.getTransaction().commit();

	}
}
