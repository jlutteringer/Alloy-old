package org.alloy.metal.spring.delegator;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.function._Predicate;
import org.alloy.metal.order.Orderable;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDelegator<T extends Delegate<N>, N> implements Delegator<T, N> {
	@Autowired
	private List<T> delegates;

	@PostConstruct
	private void init() {
		Collections.sort(delegates, Orderable.comparator());
	}

	@Override
	public T getDelegate(N delegatee) {
		T delegate = _Iterable.first(_Iterable.filter(delegates, _Predicate.reverse(delegatee)));

		if (delegate == null) {
			throw new RuntimeException("No matching delegate found for delegatee " + delegatee);
		}

		return delegate;
	}

	public List<T> getDelegates() {
		return delegates;
	}
}