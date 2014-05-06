package org.vault.base.utilities.matcher;

public class AndCompositeMatcher<T> extends AbstractCompositeMatcher<T> {
	@Override
	public boolean matches(T input) {
		for (Selector<T> matcher : internalMatchers) {
			if (!matcher.matches(input)) {
				return false;
			}
		}
		return true;
	}
}