package persistence;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.Entidade;

public abstract class EntidadePersistencia<T extends Entidade> {

	@PersistenceContext(name = "AlertaRecifeApi")
	protected EntityManager entityManager;

	public abstract void salvar(T entidade);

	public abstract void remover(T entidade);

	public abstract void atualizar(T entidade);

	public abstract T selecionaPorId(Long id);

	public abstract List<T> selecionaTodos();
}
