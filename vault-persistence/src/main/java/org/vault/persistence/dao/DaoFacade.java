package org.vault.persistence.dao;

import java.util.List;

import org.vault.base.collections.iterable.VIterables;
import org.vault.base.domain.Identifiable;
import org.vault.persistence.utilities.QueryQualifier;

public interface DaoFacade<T extends Identifiable> {
	public T find(Long id);

	public default T find(QueryQualifier qualifier) {
		return VIterables.getSingleResult(this.findAll(qualifier), true);
	}

	public List<T> findAll();

	public List<T> findAll(QueryQualifier qualifier);

	public T save(T entity);

	public void delete(T entity);
}