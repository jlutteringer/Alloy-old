package org.alloy.metal.listener;

public interface Listenable<T> {
	public ListenerRegistry<T, ?> getListeners();
}