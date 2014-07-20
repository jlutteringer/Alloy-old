package org.vault.base.utilities.matcher;

import java.util.function.Predicate;

public class ComposingSelector<T> extends AbstractSelector<T> {
	private Predicate<T> predicate;

	public ComposingSelector(Predicate<T> predicate) {
		this.predicate = predicate;
	}

	@Override
	public boolean test(T input) {
		return predicate.test(input);
	}
}
