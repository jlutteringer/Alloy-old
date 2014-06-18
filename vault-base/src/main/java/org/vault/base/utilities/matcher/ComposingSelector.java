package org.vault.base.utilities.matcher;

public class ComposingSelector<T> extends AbstractSelector<T> {
	private Matcher<T> matcher;

	public ComposingSelector(Matcher<T> matcher) {
		this.matcher = matcher;
	}

	@Override
	public boolean matches(T input) {
		return matcher.matches(input);
	}
}
