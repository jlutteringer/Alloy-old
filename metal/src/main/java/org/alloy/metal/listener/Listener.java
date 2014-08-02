package org.alloy.metal.listener;

public interface Listener<T> {
	public ListenerRegistry<T, ?> getListenable();

	public void setListenable(ListenerRegistry<T, ?> listenable);
}