package org.vault.base.delegator;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.domain.order.Orderable;
import org.vault.base.utilities.matcher.Selectors;

public abstract class AbstractDelegator<T extends Delegate<N>, N> implements Delegator<T, N> {
	@Autowired
	private List<T> delegates;

	@PostConstruct
	private void init() {
		Collections.sort(delegates, Orderable.comparator());
	}

	@Override
	public T getDelegate(N delegatee) {
		T delegate = VIterables.first(
				Selectors.reverseSelector(delegatee).getMatches(this.getDelegates()));

		if (delegate == null) {
			throw new RuntimeException("No matching delegate found for delegatee " + delegatee);
		}

		return delegate;
	}

	public List<T> getDelegates() {
		return delegates;
	}
}