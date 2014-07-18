package org.vault.base.collections.lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.vault.base.collections.MutableCollections;

import com.google.common.collect.Lists;

public class VLists {
	@SafeVarargs
	public static <T> List<T> list(T... items) {
		return Lists.newArrayList(items);
	}

	public static <T> List<T> list(Iterable<T> items) {
		return Lists.newArrayList(items);
	}

	@SafeVarargs
	public static <T> List<T> list(Iterable<T> items, T... otherItems) {
		return MutableCollections.add(list(items), otherItems);
	}

	public static <T> List<T> list(List<T> list) {
		if (list == null) {
			return Lists.newArrayList();
		}
		return list;
	}

	public static <T> Supplier<List<T>> listSupplier() {
		return () -> Lists.newArrayList();
	}

	public static <T, N> List<N> transform(Collection<T> collection, Function<T, N> transformer) {
		List<N> transformedList = Lists.newArrayList();
		for (T item : collection) {
			transformedList.add(transformer.apply(item));
		}
		return transformedList;
	}

	public static <T> List<T> add(List<T> list, Iterable<T> items) {
		List<T> newList = Lists.newArrayList();
		return MutableCollections.add(newList, items);
	}

	@SafeVarargs
	public static <T> List<T> add(List<T> list, T... items) {
		return add(list, Arrays.asList(items));
	}
}
