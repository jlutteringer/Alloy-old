package org.vault.base.observer;

public abstract class AbstractListener<T> implements Listener<T> {
	private ListenerRegistry<T, ?> listenable;

	@Override
	public ListenerRegistry<T, ?> getListenable() {
		return listenable;
	}

	@Override
	public void setListenable(ListenerRegistry<T, ?> listenable) {
		this.listenable = listenable;
	}
}