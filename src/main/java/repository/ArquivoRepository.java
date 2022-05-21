package repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import entity.Arquivo;

public class ArquivoRepository {

	private EntityManagerFactory entityManagerFactory;

	private EntityManager entityManager;

	public ArquivoRepository() {

		this.entityManagerFactory = Persistence.createEntityManagerFactory("AlertaRecifeApi");
		this.entityManager = this.entityManagerFactory.createEntityManager();
	}

	public void Salvar(Arquivo arquivo) {

		this.entityManager.getTransaction().begin();
		this.entityManager.persist(arquivo);
		this.entityManager.getTransaction().commit();
	}

	public List<Arquivo> selecionarTodos() {

		try {
			TypedQuery<Arquivo> typedQuery = this.entityManager.createNamedQuery("Arquivo.selecionarTodos",
					Arquivo.class);
			return typedQuery.getResultList();
		} catch (Exception e) {
			List<Arquivo> arquivos = new ArrayList<Arquivo>();
			return arquivos;
		}
	}

	public Arquivo selecionarPorId(Long id) {

		try {
			return this.entityManager.find(Arquivo.class, id);
		} catch (Exception e) {
			return null;
		}
	}

	public void Excluir(Long id) {

		Arquivo arquivo = this.selecionarPorId(id);

		this.entityManager.getTransaction().begin();
		this.entityManager.remove(arquivo);
		this.entityManager.getTransaction().commit();

	}

}
