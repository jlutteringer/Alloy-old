package org.alloy.metal.collections.tree;

import java.util.Collection;

import org.alloy.metal.collections.ListenableCollection;
import org.alloy.metal.collections.ListenableCollection.CollectionOperation;
import org.alloy.metal.collections._Collection;

import com.google.common.collect.ForwardingCollection;

public abstract class AbstractTree<T> extends ForwardingCollection<T> implements Tree<T> {
	@Override
	protected Collection<T> delegate() {
		ListenableCollection<T> listenableCollection = _Collection.listenableCollection(_Tree.flatten(this));
		listenableCollection.addListener(CollectionOperation.ADD, this::addChild);
		return listenableCollection;
	}

	@Override
	public String toString() {
		return _Tree.stringify(this);
	}
}