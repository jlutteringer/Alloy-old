package org.vault.base.delegator;

import org.vault.base.utilities.function.VMethod;

public interface Delegator<T extends Delegate<N>, N> {
	public T getDelegate(N delegatee);

	public default <S> S delegateAndInvoke(N delegatee, VMethod<S> invocation) {
		return this.getDelegate(delegatee).invoke(invocation);
	}
}
