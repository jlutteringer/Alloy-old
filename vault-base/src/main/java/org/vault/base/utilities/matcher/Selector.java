package org.vault.base.utilities.matcher;

public interface Selector<T> extends Matcher<T> {
	public <N extends T> Iterable<N> getMatches(Iterable<N> input);
}