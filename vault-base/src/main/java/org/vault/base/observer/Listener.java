package org.vault.base.observer;

public interface Listener<T> {
	public ListenerRegistry<T, ?> getListenable();

	public void setListenable(ListenerRegistry<T, ?> listenable);
}