package org.vault.base.collections.tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

public class HashTree<T> extends AbstractTree<T> {
	private T head;
	private List<Tree<T>> children = Lists.newArrayList();
	private List<Tree<T>> parents = Lists.newArrayList();

	private HashMap<T, Tree<T>> locate = new HashMap<T, Tree<T>>();

	public HashTree(T head) {
		this.head = head;
		locate.put(head, this);
	}

	@Override
	public void addChild(T root, T child) {
		if (locate.containsKey(root)) {
			locate.get(root).addChild(child);
		} else {
			addChild(root).addChild(child);
		}
	}

	@Override
	public Tree<T> addChild(Tree<T> child) {
		children.add(child);
		child.getParents().add(this);

		if (child instanceof HashTree) {
			((HashTree<T>) child).locate = this.locate;
			locate.put(child.getHead(), child);
		}
		return child;
	}

	@Override
	public Tree<T> addChild(T child) {
		HashTree<T> childTree = _Tree.newHashTree(child);
		return this.addChild(childTree);
	}

	@Override
	public T getHead() {
		return head;
	}

	@Override
	public Tree<T> findSubTree(T element) {
		return locate.get(element);
	}

	@Override
	public Collection<Tree<T>> getParents() {
		return parents;
	}

	@Override
	public Collection<Tree<T>> getChildren() {
		return children;
	}
}
