package org.vault.base.collections;

import java.util.Collection;

import org.vault.base.observer.Listener;

public interface CollectionListener<T> extends Listener<Collection<T>> {
	public void onAdd(T element);

	public void onRemove(Object element);
}