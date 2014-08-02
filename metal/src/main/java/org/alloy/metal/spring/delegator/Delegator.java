package org.alloy.metal.spring.delegator;

public interface Delegator<T extends Delegate<N>, N> {
	public T getDelegate(N delegatee);
}
