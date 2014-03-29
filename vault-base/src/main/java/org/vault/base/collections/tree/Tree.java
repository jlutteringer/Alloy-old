package org.vault.base.collections.tree;

import java.util.Collection;

public interface Tree<T> {
	public void addLeaf(T root, T leaf);

	public Tree<T> addLeaf(T leaf);

	public Tree<T> setAsParent(T parentRoot);

	public T getHead();

	public Tree<T> getTree(T element);

	public Tree<T> getParent();

	public Collection<T> getSuccessors(T root);

	public Collection<Tree<T>> getSubTrees();
}
