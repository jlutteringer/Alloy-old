package org.alloy.metal.collections.iterable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.alloy.metal.collections.lists._List;
import org.alloy.metal.function.NullableValue;
import org.alloy.metal.function.StatefulSupplier;
import org.alloy.metal.function._Predicate;
import org.alloy.metal.function.equality.SymmetricEqualitor;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class _Iterable {
	public static <T> Iterable<T> createFromIteratorGenerator(Supplier<Iterator<T>> supplier) {
		return new GeneratingIterable<T>(supplier);
	}

	public static <T, N> Iterable<T> createFromElementSupplier(StatefulSupplier<N, NullableValue<T>> supplier, Supplier<N> stateGenerator) {
		return _Iterable.createFromIteratorGenerator(_Iterable.createIteratorGeneratorFromElementGenerator(supplier, stateGenerator));
	}

	private static <T, N> Supplier<Iterator<T>> createIteratorGeneratorFromElementGenerator(StatefulSupplier<N, NullableValue<T>> supplier, Supplier<N> stateGenerator) {
		return () -> new GeneratingIterator<T, N>(supplier, stateGenerator.get());
	}

	public static <T, N> Iterable<N> multiplexingIterable(Iterable<T> iterable, Function<T, Iterator<N>> transformer) {
		IteratorSupplierContext<T, N> context = new IteratorSupplierContext<>(iterable);
		context.setTransformer(transformer);
		return _Iterable.createFromElementSupplier(context.getPrimarySupplier(), context.getStateSupplier());
	}

	public static <T> Iterable<T> flatten(Iterable<? extends Iterable<T>> multiIterator) {
		return _Iterable.multiplexingIterable(multiIterator, (interalIterator) -> interalIterator.iterator());
	}

	public static <T> Iterable<T> unique(Iterable<T> iterable, SymmetricEqualitor<T> equality) {
		return filter(iterable, _Predicate.matchSeen(equality));
	}

	public static <T> boolean contains(Iterable<T> iterable, T item, SymmetricEqualitor<T> equality) {
		for (T element : iterable) {
			if (equality.apply(item, element)) {
				return true;
			}
		}

		return false;
	}

	public static <T> T filterSingleResult(Iterable<T> iterable, Predicate<? super T> filter) {
		return filterSingleResult(iterable, filter, false);
	}

	public static <T> T filterSingleResult(Iterable<T> iterable, Predicate<? super T> filter, boolean allowEmpty) {
		return getSingleResult(filter(iterable, filter), allowEmpty);
	}

	public static <T> Iterable<T> filter(Iterable<T> iterable, Predicate<? super T> filter) {
		IteratorSupplierContext<T, T> context = new IteratorSupplierContext<>(iterable);
		context.setFilter(filter);
		return _Iterable.createFromElementSupplier(context.getPrimarySupplier(), context.getStateSupplier());
	}

	public static <T, N> Iterable<N> transform(Iterable<T> iterable, Function<T, N> transformer) {
		IteratorSupplierContext<T, N> context = new IteratorSupplierContext<>(iterable);
		context.setTransformer(_Iterable.singletonIteratorTransformer(transformer));
		return _Iterable.createFromElementSupplier(context.getPrimarySupplier(), context.getStateSupplier());
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
		return _Iterable.getSingleResult(iterable, false);
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
		List<T> backingList = _List.list(iterator);
		Collections.reverse(backingList);
		return backingList;
	}

	public static <T> boolean compareElements(Iterable<T> first, Iterable<T> second, boolean orderMatters) {
		if (orderMatters) {
			// FUTURE
			throw new RuntimeException();
		}
		else {
			return HashMultiset.create(first).equals(HashMultiset.create(second));
		}
	}
}
