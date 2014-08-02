package org.alloy.metal.collections.tree;

import java.util.Collection;
import java.util.List;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.function.NullableValue;
import org.alloy.metal.function._Tuple;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

public class _Tree {
	public static <T> HashTree<T> newHashTree(T head) {
		return new HashTree<T>(head);
	}

	public static <T> Collection<T> flatten(Tree<T> tree) {
		List<T> flattened = Lists.newArrayList();
		for (T element : _Tree.iterateBreadthFirst(tree)) {
			flattened.add(element);
		}
		return flattened;
	}

	public static <T> Iterable<T> iterateBreadthFirst(final Tree<T> topLevelTree) {
		return _Iterable.createFromElementSupplier(
				(context) -> {
					Tree<T> tree = null;
					for (Tree<T> elementToProcess : context.getFirst()) {
						if (context.getSecond().containsAll(elementToProcess.getParents())) {
							tree = elementToProcess;
							break;
						}
					}

					if (tree == null) {
						return NullableValue.none();
					}

					context.getFirst().remove(tree);
					for (Tree<T> subTree : tree.getChildren()) {
						context.getFirst().add(subTree);
					}

					context.getSecond().add(tree);
					return NullableValue.of(tree.getHead());
				},
				() -> {
					Collection<Tree<T>> elementsToProcess = Sets.newHashSet();
					Collection<Tree<T>> visitedElements = Sets.newHashSet();
					elementsToProcess.add(topLevelTree);
					return _Tuple.of(elementsToProcess, visitedElements);
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
