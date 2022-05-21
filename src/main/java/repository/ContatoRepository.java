package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Contato;

public class ContatoRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public ContatoRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(Contato contato) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(contato);
		this.entityManager.getTransaction().commit();
	}

	public void Alterar(Contato contato) {

		this.entityManager.getTransaction().begin();
		this.entityManager.merge(contato);
		this.entityManager.getTransaction().commit();
	}

	public List<Contato> selecionarTodos() {

		try {
			TypedQuery<Contato> typedQuery = this.entityManager.createNamedQuery("Contato.selecionarTodos",
					Contato.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Contato> contatos = new ArrayList<Contato>();
			return contatos;
		}
	}

	public Contato selecionarPorId(Long id) {

		try {
			return this.entityManager.find(Contato.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public void Excluir(Long id) {

		Contato contato = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(contato);
		this.entityManager.getTransaction().commit();

	}
}
