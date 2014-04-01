package org.vault.base.observer;

import java.util.List;

import org.vault.base.utilities.function.Operation.Operation_V1;

import com.google.common.collect.Lists;

public class AbstractListenerRegistry<T, N extends Listener<T>> implements ListenerRegistry<T, N> {
	private T parent;
	private List<N> listeners = Lists.newArrayList();

	@Override
	public T getParent() {
		return parent;
	}

	public void setParent(T parent) {
		this.parent = parent;
	}

	@Override
	public void add(N listener) {
		listener.setListenable(this);
		listeners.add(listener);
	}

	@Override
	public List<N> getListeners() {
		return listeners;
	}

	@Override
	public void apply(Operation_V1<N> operation) {
		for (N listener : listeners) {
			operation.apply(listener);
		}
	}
}