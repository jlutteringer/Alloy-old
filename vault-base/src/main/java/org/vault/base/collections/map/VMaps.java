package org.vault.base.collections.map;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import com.google.common.collect.Maps;

public class VMaps {
	public static <T, N> HashMap<T, N> defaultHashMap(Supplier<N> supplier) {
		return new DefaultHashMap<>(supplier);
	}

	public static <T, N> Supplier<Map<T, N>> mapSupplier() {
		return () -> Maps.newHashMap();
	}

	public static <T, N> Supplier<Map<T, N>> concurrentMapSupplier() {
		return () -> Maps.newConcurrentMap();
	}
}
