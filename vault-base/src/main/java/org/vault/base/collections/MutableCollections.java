package org.vault.base.collections;

import java.util.Arrays;
import java.util.Collection;

public class MutableCollections {
	public static <T, N extends Collection<T>> N add(N collection, Iterable<T> items) {
		items.forEach((item) -> collection.add(item));
		return collection;
	}

	@SafeVarargs
	public static <T, N extends Collection<T>> N add(N collection, T... items) {
		return add(collection, Arrays.asList(items));
	}
}