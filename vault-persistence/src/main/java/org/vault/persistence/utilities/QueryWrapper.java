package org.vault.persistence.utilities;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import org.alloy.metal.collections.lists._List;

import com.google.common.collect.Iterables;

public class QueryWrapper<T> {
	private TypedQuery<T> query;

	public QueryWrapper(TypedQuery<T> query) {
		this.query = query;
	}

	public QueryWrapper<T> setParameter(String name, Object value) {
		query.setParameter(name, value);
		return this;
	}

	public List<T> getResults() {
		return _List.list(query.getResultList());
	}

	public Optional<T> getSingleResult() {
		return Optional.of(Iterables.getFirst(getResults(), null));
	}
}
