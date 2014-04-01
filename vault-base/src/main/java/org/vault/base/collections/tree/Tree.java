package org.vault.base.collections.tree;

import java.util.Collection;

public interface Tree<T> extends Collection<T> {
	public Tree<T> addChild(T child);

	public void addChild(T root, T child);

	public Tree<T> getParent();

	public T getHead();

	public Collection<Tree<T>> getChildren();

	public Tree<T> findSubTree(T element);
}
