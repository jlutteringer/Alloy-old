package org.vault.base.delegator;

import java.util.function.Predicate;

import org.vault.base.reflection.VReflection;

public abstract class ClassTypeDelegate<T, N> extends AbstractDelegate<N> {
	@Override
	protected Predicate<N> getInternalMatcher() {
		return (input) -> input.getClass().equals(VReflection.getTypeArguments(ClassTypeDelegate.class, this.getClass()).get(0));
	}
}
