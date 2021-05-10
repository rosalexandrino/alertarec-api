package persistence;

import java.util.List;
import model.Entidade;

public abstract class EntidadePersistencia<T extends Entidade> {
	
	public abstract void salvar(T entidade);

	public abstract void remover(T entidade);

	public abstract void atualizar(T entidade);

	public abstract T selecionaPorId(Long id);

	public abstract List<T> selecionaTodos();
}
