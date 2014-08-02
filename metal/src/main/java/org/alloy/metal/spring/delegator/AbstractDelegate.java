package org.alloy.metal.spring.delegator;

import java.util.function.Predicate;

import org.alloy.metal.spring.AlloyBean;

public abstract class AbstractDelegate<N> extends AlloyBean implements Delegate<N> {
	@Override
	public boolean test(N delegatee) {
		return this.getInternalMatcher().test(delegatee);
	}

	@Override
	public int getOrder() {
		return 0;
	}

	protected abstract Predicate<N> getInternalMatcher();
}
