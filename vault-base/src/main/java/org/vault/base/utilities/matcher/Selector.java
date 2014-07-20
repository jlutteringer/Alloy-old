package org.vault.base.utilities.matcher;

import java.util.function.Predicate;

public interface Selector<T> extends Predicate<T> {
	public <N extends T> Iterable<N> getMatches(Iterable<N> input);
}