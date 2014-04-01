package org.vault.base.observer;

public interface Listenable<T> {
	public ListenerRegistry<T, ?> getListeners();
}