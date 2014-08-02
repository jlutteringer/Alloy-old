package org.alloy.metal.collections;

import java.util.Collection;

import org.alloy.metal.listener.AbstractListener;

public abstract class AbstractCollectionListener<T> extends AbstractListener<Collection<T>> implements CollectionListener<T> {
	@Override
	public void onAdd(T element) {
		// Do nothing
	}

	@Override
	public void onRemove(Object element) {
		// Do nothing
	}
}