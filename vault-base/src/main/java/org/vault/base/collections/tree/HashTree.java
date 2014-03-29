package org.vault.base.collections.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class HashTree<T> implements Tree<T> {
	private T head;

	private ArrayList<Tree<T>> leafs = new ArrayList<Tree<T>>();

	private Tree<T> parent = null;

	private HashMap<T, Tree<T>> locate = new HashMap<T, Tree<T>>();

	public HashTree(T head) {
		this.head = head;
		locate.put(head, this);
	}

	public void addLeaf(T root, T leaf) {
		if (locate.containsKey(root)) {
			locate.get(root).addLeaf(leaf);
		} else {
			addLeaf(root).addLeaf(leaf);
		}
	}

	public Tree<T> addLeaf(T leaf) {
		HashTree<T> t = Trees.newHashTree(leaf);
		leafs.add(t);
		t.parent = this;
		t.locate = this.locate;
		locate.put(leaf, t);
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

	public T getHead() {
		return head;
	}

	public Tree<T> getTree(T element) {
		return locate.get(element);
	}

	public Tree<T> getParent() {
		return parent;
	}

	public Collection<T> getSuccessors(T root) {
		Collection<T> successors = new ArrayList<T>();
		Tree<T> tree = getTree(root);
		if (null != tree) {
			for (Tree<T> leaf : tree.getSubTrees()) {
				successors.add(leaf.getHead());
			}
		}
		return successors;
	}

	public Collection<Tree<T>> getSubTrees() {
		return leafs;
	}
}
