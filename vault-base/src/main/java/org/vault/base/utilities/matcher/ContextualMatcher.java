package org.vault.base.utilities.matcher;

public interface ContextualMatcher<T, N> extends Matcher<T> {
	public Boolean hasContext();

	public void setContext(N context);
}