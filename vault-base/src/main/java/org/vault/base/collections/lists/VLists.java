package org.vault.base.collections.lists;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.collect.Lists;

public class VLists {
	@SafeVarargs
	public static <T> List<T> list(T... items) {
		return Lists.newArrayList(items);
	}

	public static <T> List<T> list(Iterable<T> items) {
		return Lists.newArrayList(items);
	}

	public static <T> Supplier<List<T>> listSupplier() {
		return () -> Lists.newArrayList();
	}
}
