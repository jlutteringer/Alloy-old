package org.vault.base.utilities.matcher;

@FunctionalInterface
public interface Matcher<T> {
	public boolean matches(T input);
}
