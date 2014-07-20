package org.vault.base.collections.tree;

import java.util.Collection;

import org.vault.base.collections.BackedCollection;
import org.vault.base.collections.AbstractCollectionListener;
import org.vault.base.collections.VCollections;

public abstract class AbstractTree<T> extends BackedCollection<T> implements Tree<T> {
	@Override
	public Collection<T> getBackingCollection() {
		return VCollections.listenableCollection(Trees.flatten(this), new AbstractCollectionListener<T>() {
			@Override
			public void onAdd(T element) {
				addChild(element);
			}
		});
	}

	@Override
	public String toString() {
		return Trees.stringify(this);
	}
}