package org.vault.base.collections.tree;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.google.common.collect.Lists;

public class HashTree<T> extends AbstractTree<T> {
	private T head;
	private List<Tree<T>> leafs = Lists.newArrayList();

	private Tree<T> parent = null;
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
	public Tree<T> addChild(T child) {
		HashTree<T> t = Trees.newHashTree(child);
		leafs.add(t);
		t.parent = this;
		t.locate = this.locate;
		locate.put(child, t);
		return t;
	}

	public Tree<T> setAsParent(T parentRoot) {
		HashTree<T> t = Trees.newHashTree(parentRoot);
		t.leafs.add(this);
		this.parent = t;
		t.locate = this.locate;
		t.locate.put(head, this);
		t.locate.put(parentRoot, t);
		return t;
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
	public Tree<T> getParent() {
		return parent;
	}

	@Override
	public Collection<Tree<T>> getChildren() {
		return leafs;
	}
}
