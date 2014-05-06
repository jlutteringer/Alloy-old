package org.vault.base.utilities.function;

import java.util.function.Function;

public class VFunctions {
	public static interface Accumulator<T> extends Function<T, Long> {

	}

	public static <T> long accumulate(Iterable<T> iterable, Accumulator<T> accumulator) {
		long total = 0;
		for (T element : iterable) {
			total = total + accumulator.apply(element);
		}
		return total;
	}

	@SuppressWarnings("unchecked")
	public static <T, N> Function<T, N> same() {
		return (value) -> ((N) value);
	}
}