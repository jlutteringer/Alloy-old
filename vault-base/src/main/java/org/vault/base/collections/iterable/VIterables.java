package org.vault.base.collections.iterable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.vault.base.collections.lists.VLists;
import org.vault.base.utilities.Value;
import org.vault.base.utilities.function.StatefulSupplier;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class VIterables {
	public static <T> Iterable<T> createFromIteratorGenerator(Supplier<Iterator<T>> supplier) {
		return new GeneratingIterable<T>(supplier);
	}

	public static <T, N> Iterable<T> createFromElementSupplier(StatefulSupplier<N, Value<T>> supplier, Supplier<N> stateGenerator) {
		return VIterables.createFromIteratorGenerator(VIterables.createIteratorGeneratorFromElementGenerator(supplier, stateGenerator));
	}

	private static <T, N> Supplier<Iterator<T>> createIteratorGeneratorFromElementGenerator(StatefulSupplier<N, Value<T>> supplier, Supplier<N> stateGenerator) {
		return () -> new GeneratingIterator<T, N>(supplier, stateGenerator.get());
	}

	public static <T, N> Iterable<N> multiplexingIterable(Iterable<T> iterable, Function<T, Iterator<N>> transformer) {
		IteratorSupplierContext<T, N> context = new IteratorSupplierContext<>(iterable);
		context.setTransformer(transformer);
		return VIterables.createFromElementSupplier(context.getPrimarySupplier(), context.getStateSupplier());
	}

	public static <T> Iterable<T> flatten(Iterable<? extends Iterable<T>> multiIterator) {
		return VIterables.multiplexingIterable(multiIterator, (interalIterator) -> interalIterator.iterator());
	}

	public static <T> Iterable<T> filter(Iterable<T> iterable, Predicate<? super T> filter) {
		IteratorSupplierContext<T, T> context = new IteratorSupplierContext<>(iterable);
		context.setFilter(filter);
		return VIterables.createFromElementSupplier(context.getPrimarySupplier(), context.getStateSupplier());
	}

	public static <T, N> Iterable<N> transform(Iterable<T> iterable, Function<T, N> transformer) {
		IteratorSupplierContext<T, N> context = new IteratorSupplierContext<>(iterable);
		context.setTransformer(VIterables.singletonIteratorTransformer(transformer));
		return VIterables.createFromElementSupplier(context.getPrimarySupplier(), context.getStateSupplier());
	}

	@SuppressWarnings("unchecked")
	public static <T, N> Function<T, Iterator<N>> singletonIteratorTransformer(Function<T, N> transformer) {
		return (value) -> {
			N transformedValue = transformer.apply(value);
			return Lists.newArrayList(transformedValue).iterator();
		};
	}

	public static <T> T first(Iterable<T> iterable) {
		return Iterables.getFirst(iterable, null);
	}

	public static <T> T getSingleResult(Iterable<T> iterable) {
		return VIterables.getSingleResult(iterable, false);
	}

	public static <T> T getSingleResult(Iterable<T> iterable, boolean allowEmpty) {
		Iterator<T> iterator = iterable.iterator();
		if (!iterator.hasNext() && !allowEmpty) {
			throw new RuntimeException("Empty collection passed to get single result with allow empty = false");
		}
		else if (!iterator.hasNext()) {
			return null;
		}

		T value = iterator.next();

		if (iterator.hasNext()) {
			throw new RuntimeException("Multiple results passed to get single result");
		}

		return value;
	}

	public static <T> Iterable<T> reverse(Iterable<T> iterator) {
		List<T> backingList = VLists.list(iterator);
		Collections.reverse(backingList);
		return backingList;
	}
}
