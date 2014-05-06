package org.vault.base.utilities.matcher;

public interface Selector<T> extends Matcher<T> {
	public Iterable<T> getMatches(Iterable<T> input);
}