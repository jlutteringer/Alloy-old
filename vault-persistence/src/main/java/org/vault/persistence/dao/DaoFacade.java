package org.vault.persistence.dao;

import java.util.List;

import org.vault.base.collections.iterable._Iterable;
import org.vault.base.object.Identifiable;
import org.vault.persistence.utilities.QueryQualifier;

public interface DaoFacade<T extends Identifiable> {
	public T create();

	public T find(Long id);

	public default T find(QueryQualifier qualifier) {
		return _Iterable.getSingleResult(this.findAll(qualifier), true);
	}

	public List<T> findAll();

	public List<T> findAll(QueryQualifier qualifier);

	public T save(T entity);

	public void delete(T entity);
}
