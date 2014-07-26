package org.vault.base.delegator;

import java.util.function.Predicate;

import org.vault.base.spring.beans.VaultBean;

public abstract class AbstractDelegate<N> extends VaultBean implements Delegate<N> {
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
