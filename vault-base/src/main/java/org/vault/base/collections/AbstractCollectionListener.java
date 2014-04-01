package org.vault.base.collections;

import java.util.Collection;

import org.vault.base.observer.AbstractListener;

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