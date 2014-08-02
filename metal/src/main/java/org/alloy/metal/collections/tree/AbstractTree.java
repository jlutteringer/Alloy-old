package org.alloy.metal.collections.tree;

import java.util.Collection;

import org.alloy.metal.collections.AbstractCollectionListener;
import org.alloy.metal.collections.BackedCollection;
import org.alloy.metal.collections._Collection;

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