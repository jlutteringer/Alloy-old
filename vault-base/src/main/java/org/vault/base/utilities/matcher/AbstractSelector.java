package org.vault.base.utilities.matcher;

import org.vault.base.collections.iterable._Iterable;

public abstract class AbstractSelector<T> implements Selector<T> {
	@Override
	public <N extends T> Iterable<N> getMatches(Iterable<N> inputs) {
		return _Iterable.filter(inputs, this);
	}
}