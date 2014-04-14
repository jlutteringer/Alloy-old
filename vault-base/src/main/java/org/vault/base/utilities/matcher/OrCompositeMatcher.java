package org.vault.base.utilities.matcher;

public class OrCompositeMatcher<T> extends AbstractCompositeMatcher<T> {
	@Override
	public boolean matches(T input) {
		for (Matcher<T> matcher : internalMatchers) {
			if (matcher.matches(input)) {
				return true;
			}
		}
		return false;
	}
}