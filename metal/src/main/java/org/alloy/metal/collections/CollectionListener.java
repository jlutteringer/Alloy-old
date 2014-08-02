package org.alloy.metal.collections;

import java.util.Collection;

import org.alloy.metal.listener.Listener;

public interface CollectionListener<T> extends Listener<Collection<T>> {
	public void onAdd(T element);

	public void onRemove(Object element);
}