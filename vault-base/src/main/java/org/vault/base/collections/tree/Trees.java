package org.vault.base.collections.tree;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang3.StringUtils;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.utilities.Value;

import com.google.common.collect.Lists;

public class Trees {
	public static <T> HashTree<T> newHashTree(T head) {
		return new HashTree<T>(head);
	}

	public static <T> Collection<T> flatten(Tree<T> tree) {
		List<T> flattened = Lists.newArrayList();
		for (T element : Trees.iterateBreadthFirst(tree)) {
			flattened.add(element);
		}
		return flattened;
	}

	public static <T> Iterable<T> iterateBreadthFirst(final Tree<T> topLevelTree) {
		return VIterables.createFromElementSupplier(
				(queue) -> {
					try {
						Tree<T> tree = queue.remove();
						for (Tree<T> subTree : tree.getChildren()) {
							queue.add(subTree);
						}
						return Value.of(tree.getHead());
					}
					catch (NoSuchElementException e) {
						return Value.none();
					}
				},
				() -> {
					Queue<Tree<T>> bfsQueue = new LinkedList<Tree<T>>();
					bfsQueue.add(topLevelTree);
					return bfsQueue;
				});
	}

	public static <T> Iterable<T> iterateDepthFirst(Tree<T> topLevelTree) {
		return VIterables.createFromElementSupplier(
				(stack) -> {
					try {
						Tree<T> tree = stack.pop();
						for (Tree<T> subTree : tree.getChildren()) {
							stack.add(subTree);
						}
						return Value.of(tree.getHead());
					}
					catch (EmptyStackException e) {
						return Value.none();
					}
				},
				() -> {
					Stack<Tree<T>> dfsStack = new Stack<Tree<T>>();
					dfsStack.push(topLevelTree);
					return dfsStack;
				});
	}

	public static <T> String stringify(Tree<T> tree) {
		StringBuilder builder = new StringBuilder();
		stringifyInternal(tree, builder, 0);
		return builder.toString();
	}

	private static <T> void stringifyInternal(Tree<T> tree, StringBuilder builder, int depth) {
		builder.append(StringUtils.repeat("\t", depth));
		builder.append(tree.getHead().toString());
		builder.append("\n");

		for (Tree<T> child : tree.getChildren()) {
			stringifyInternal(child, builder, depth + 1);
		}
	}
}
