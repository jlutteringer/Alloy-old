package org.vault.base.delegator;

public interface Delegator<T extends Delegate<N>, N> {
	public T getDelegate(N delegatee);
}
