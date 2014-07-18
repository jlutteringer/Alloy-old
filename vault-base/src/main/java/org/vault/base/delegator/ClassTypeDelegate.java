package org.vault.base.delegator;

import org.vault.base.reflection.VReflection;
import org.vault.base.utilities.matcher.Matcher;

public abstract class ClassTypeDelegate<T, N> extends AbstractDelegate<N> {
	@Override
	protected Matcher<N> getInternalMatcher() {
		return (input) -> input.getClass().equals(VReflection.getTypeArguments(ClassTypeDelegate.class, this.getClass()).get(0));
	}
}
