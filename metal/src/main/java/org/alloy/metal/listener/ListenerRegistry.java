package org.alloy.metal.listener;

import java.util.List;
import java.util.function.Consumer;

public interface ListenerRegistry<T, N extends Listener<T>> {
	public T getParent();

	public void add(N listener);

	public List<N> getListeners();

	public void apply(Consumer<N> operation);
}