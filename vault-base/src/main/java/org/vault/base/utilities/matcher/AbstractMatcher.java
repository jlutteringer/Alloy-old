package org.vault.base.utilities.matcher;

import org.vault.base.collections.iterable.VIterables;

public abstract class AbstractMatcher<T> implements Selector<T> {

	@Override
	public abstract boolean matches(T input);

	@Override
	public Iterable<T> getMatches(Iterable<T> inputs) {
		return VIterables.matchingIterable(inputs, this);
	}
}