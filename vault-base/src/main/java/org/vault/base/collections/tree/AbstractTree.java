package org.vault.base.collections.tree;

import java.util.Collection;

import org.vault.base.collections.BackedCollection;
import org.vault.base.collections.AbstractCollectionListener;
import org.vault.base.collections._Collection;

public abstract class AbstractTree<T> extends BackedCollection<T> implements Tree<T> {
	@Override
	public Collection<T> getBackingCollection() {
		return _Collection.listenableCollection(_Tree.flatten(this), new AbstractCollectionListener<T>() {
			@Override
			public void onAdd(T element) {
				addChild(element);
			}
		});
	}

	@Override
	public String toString() {
		return _Tree.stringify(this);
	}
}