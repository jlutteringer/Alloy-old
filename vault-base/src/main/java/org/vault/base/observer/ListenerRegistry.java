package org.vault.base.observer;

import java.util.List;

import org.vault.base.utilities.function.Operation.Operation_V1;

public interface ListenerRegistry<T, N extends Listener<T>> {
	public T getParent();

	public void add(N listener);

	public List<N> getListeners();

	public void apply(Operation_V1<N> operation);
}