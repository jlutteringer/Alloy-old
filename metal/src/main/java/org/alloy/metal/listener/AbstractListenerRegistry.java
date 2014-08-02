package org.alloy.metal.listener;

import java.util.List;
import java.util.function.Consumer;

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
	public void apply(Consumer<N> operation) {
		for (N listener : listeners) {
			operation.accept(listener);
		}
	}
}