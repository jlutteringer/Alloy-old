package org.vault.base.collections.tree;

import java.util.Iterator;

import org.vault.base.collections.iterable.V_Iterables;
import org.vault.base.utilities.function.Generator;

public class Trees {
	public static <T> HashTree<T> newHashTree(T head) {
		return new HashTree<T>(head);
	}

	public static <T> Iterable<T> breadthFirstSearch(Tree<T> tree) {
		return V_Iterables.create(new Generator<Iterator<T>>() {
			public Iterator<T> apply() {
				return new Iterator<T>() {
					public boolean hasNext() {
						// JOHN Auto-generated method stub
						return false;
					}

					public T next() {
						// JOHN Auto-generated method stub
						return null;
					}

					public void remove() {
						// JOHN Auto-generated method stub
					}
				};
			}
		});
	}

	public static <T> Iterable<T> depthFirstSearch(Tree<T> tree) {
		return null;
	}
}
