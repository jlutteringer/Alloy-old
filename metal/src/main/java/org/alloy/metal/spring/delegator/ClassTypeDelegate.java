package org.alloy.metal.spring.delegator;

import java.util.function.Predicate;

import org.alloy.metal.reflection._Reflection;

public abstract class ClassTypeDelegate<T, N> extends AbstractDelegate<N> {
	@Override
	protected Predicate<N> getInternalMatcher() {
		return (input) -> input.getClass().equals(_Reflection.getTypeArguments(ClassTypeDelegate.class, this.getClass()).get(0));
	}
}
