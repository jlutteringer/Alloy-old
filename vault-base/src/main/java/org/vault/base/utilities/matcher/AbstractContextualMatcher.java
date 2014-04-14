package org.vault.base.utilities.matcher;

public abstract class AbstractContextualMatcher<T, N> extends AbstractMatcher<T> implements ContextualMatcher<T, N> {

	protected N context;

	@Override
	public Boolean hasContext() {
		return context != null;
	}

	@Override
	public void setContext(N context) {
		this.context = context;
	}

	@Override
	public abstract boolean matches(T input);
}