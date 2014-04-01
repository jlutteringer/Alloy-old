package org.vault.base.collections.tree;

import java.util.Collection;
import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.vault.base.collections.iterable.V_Iterables;
import org.vault.base.utilities.function.Generator;

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

	public static <T> Iterable<T> iterateBreadthFirst(Tree<T> tree) {
		final Queue<Tree<T>> bfsQueue = new LinkedList<Tree<T>>();
		bfsQueue.add(tree);

		return V_Iterables.createFromElementGenerator(new Generator<T>() {
			@Override
			public T apply() {
				Tree<T> tree = bfsQueue.remove();
				for (Tree<T> subTree : tree.getChildren()) {
					bfsQueue.add(subTree);
				}
				return tree.getHead();
			}
		});
	}

	public static <T> Iterable<T> iterateDepthFirst(Tree<T> tree) {
		final Stack<Tree<T>> dfsStack = new Stack<Tree<T>>();
		dfsStack.push(tree);

		return V_Iterables.createFromElementGenerator(new Generator<T>() {
			@Override
			public T apply() {
				try {
					Tree<T> tree = dfsStack.pop();
					for (Tree<T> subTree : tree.getChildren()) {
						dfsStack.add(subTree);
					}
					return tree.getHead();
				}
				catch (EmptyStackException e) {
					throw new NoSuchElementException();
				}
			}
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
