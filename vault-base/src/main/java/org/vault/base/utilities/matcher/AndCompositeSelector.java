package org.vault.base.utilities.matcher;

public class AndCompositeSelector<T> extends AbstractCompositeSelector<T> {
	@Override
	public boolean test(T input) {
		for (Selector<T> matcher : internalSelectors) {
			if (!matcher.test(input)) {
				return false;
			}
		}
		return true;
	}
}