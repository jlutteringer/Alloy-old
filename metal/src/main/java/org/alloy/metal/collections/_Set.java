package org.alloy.metal.collections;

import java.util.Set;

import com.google.common.collect.Sets;

public class _Set {
	@SafeVarargs
	public static <T> Set<T> set(T... items) {
		return Sets.newHashSet(items);
	}

	public static <T> Set<T> set(Iterable<T> iterable) {
		return Sets.newHashSet(iterable);
	}
}