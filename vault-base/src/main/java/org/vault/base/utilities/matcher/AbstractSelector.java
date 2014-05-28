package org.vault.base.utilities.matcher;

import org.vault.base.collections.iterable.VIterables;

public abstract class AbstractSelector<T> implements Selector<T> {

	@Override
	public abstract boolean matches(T input);

	@Override
	public <N extends T> Iterable<N> getMatches(Iterable<N> inputs) {
		return VIterables.matchingIterable(inputs, this);
	}
}