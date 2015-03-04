package org.alloy.persistence.utilities;

import java.util.Optional;

import javax.persistence.TypedQuery;

import org.alloy.metal.collections.list.AList;
import org.alloy.metal.collections.list._Lists;

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

	public AList<T> getResults() {
		return _Lists.wrap(query.getResultList());
	}

	public Optional<T> getSingleResult() {
		return Optional.of(Iterables.getFirst(getResults(), null));
	}
}
