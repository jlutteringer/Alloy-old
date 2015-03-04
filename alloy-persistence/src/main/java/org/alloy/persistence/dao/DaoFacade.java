package org.alloy.persistence.dao;

import java.util.Optional;

import org.alloy.metal.collections.list.AList;
import org.alloy.metal.domain.Identifiable;
import org.alloy.persistence.utilities.QueryQualifier;

public interface DaoFacade<T extends Identifiable> {
	public T create();

	public Optional<T> find(long id);

	public default T findStrict(long id) {
		return this.find(id).get();
	}

	public default Optional<T> find(QueryQualifier qualifier) {
		return this.findAll(qualifier).first();
	}

	public default T findStrict(QueryQualifier qualifier) {
		return this.find(qualifier).get();
	}

	public AList<T> findAll();

	public AList<T> findAll(QueryQualifier qualifier);

	public T save(T entity);

	public void delete(T entity);
}