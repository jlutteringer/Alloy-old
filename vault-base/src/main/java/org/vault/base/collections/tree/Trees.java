package org.vault.base.collections.tree;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

import org.vault.base.collections.iterable.V_Iterables;
import org.vault.base.utilities.function.Generator;

public class Trees {
	public static <T> HashTree<T> newHashTree(T head) {
		return new HashTree<T>(head);
	}

	public static <T> Iterable<T> iterateBreadthFirst(Tree<T> tree) {
		final Queue<Tree<T>> bfsQueue = new LinkedList<Tree<T>>();
		bfsQueue.add(tree);
		
		return V_Iterables.createFromElementGenerator(new Generator<T>(){
			public T apply() {
				Tree<T> tree = bfsQueue.remove();
				for(Tree<T> subTree : tree.getSubTrees()) {
					bfsQueue.add(subTree);
				}
				return tree.getHead();
			}
		});
	}

	public static <T> Iterable<T> iterateDepthFirst(Tree<T> tree) {
		final Stack<Tree<T>> dfsStack = new Stack<Tree<T>>();
		dfsStack.push(tree);
		
		return V_Iterables.createFromElementGenerator(new Generator<T>(){
			public T apply() {
				try {
					Tree<T> tree = dfsStack.pop();
					for(Tree<T> subTree : tree.getSubTrees()) {
						dfsStack.add(subTree);
					}
					return tree.getHead();
				}
				catch(EmptyStackException e) {
					throw new NoSuchElementException();
				}
			}
		});
	}
}
