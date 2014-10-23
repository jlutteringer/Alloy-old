package org.alloy.metal.collections.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.alloy.metal.collections._Iterator;
import org.alloy.metal.collections.iterable.IteratorSupplierContext.IteratorSupplierState;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.function.NullableValue;
import org.alloy.metal.function.StatefulSupplier;
import org.alloy.metal.function.SupplierContext;
import org.alloy.metal.function._Function;
import org.alloy.metal.function._Predicate;

import com.google.common.collect.Iterators;

public class IteratorSupplierContext<T, N> implements SupplierContext<IteratorSupplierState<T, N>, NullableValue<N>> {
	private Iterable<T> iterable;
	private IteratorProcessor<T, N> processor = new DefaultIteratorProcessor<>();

	private Function<T, Iterable<N>> transformer = _Iterable.singletonIteratorTransformer(_Function.cast());
	private Predicate<? super N> filter = _Predicate.matchAll();

	public IteratorSupplierContext(Iterable<T> iterable) {
		this.iterable = iterable;
	}

	@Override
	public StatefulSupplier<IteratorSupplierState<T, N>, NullableValue<N>> getPrimarySupplier() {
		return (state) -> {
			return this.getNext(state);
		};
	}

	@Override
	public Supplier<IteratorSupplierState<T, N>> getStateSupplier() {
		return () -> {
			return new IteratorSupplierState<>(iterable.iterator());
		};
	}

	private NullableValue<N> getNext(IteratorSupplierState<T, N> state) {
		boolean finished = false;
		while (!finished) {
			try {
				return NullableValue.of(_Iterator.next(state.getResults(), filter));
			} catch (NoSuchElementException e) {
				if (state.getValuesToProcess().hasNext()) {
					processor.processValues(state, transformer);
				}
				else {
					finished = true;
				}
			}
		}

		return NullableValue.none();
	}

	public Predicate<? super N> getFilter() {
		return filter;
	}

	public void setFilter(Predicate<? super N> filter) {
		this.filter = filter;
	}

	public Function<T, Iterable<N>> getTransformer() {
		return transformer;
	}

	public void setTransformer(Function<T, Iterable<N>> transformer) {
		this.transformer = transformer;
	}

	public void setProcessor(IteratorProcessor<T, N> processor) {
		this.processor = processor;
	}

	public static class IteratorSupplierState<T, N> {
		private Iterator<T> valuesToProcess;
		private Iterator<N> results;

		public IteratorSupplierState(Iterator<T> valuesToProcess) {
			this.valuesToProcess = valuesToProcess;
		}

		public Iterator<T> getValuesToProcess() {
			return valuesToProcess;
		}

		public void setValuesToProcess(Iterator<T> valuesToProcess) {
			this.valuesToProcess = valuesToProcess;
		}

		public Iterator<N> getResults() {
			if (results == null) {
				return Iterators.emptyIterator();
			}
			return results;
		}

		public void setResults(Iterator<N> results) {
			this.results = results;
		}
	}

	public interface IteratorProcessor<T, N> {
		public void processValues(IteratorSupplierState<T, N> state, Function<T, Iterable<N>> transformer);
	}

	public static class DefaultIteratorProcessor<T, N> implements IteratorProcessor<T, N> {
		@Override
		public void processValues(IteratorSupplierState<T, N> state, Function<T, Iterable<N>> transformer) {
			T value = state.getValuesToProcess().next();
			Iterator<N> results = transformer.apply(value).iterator();
			state.setResults(results);
		}
	}

	public static class BreadthTraversingIteratorProcessor<T> implements IteratorProcessor<T, T> {
		@Override
		public void processValues(IteratorSupplierState<T, T> state, Function<T, Iterable<T>> transformer) {
			T value = state.getValuesToProcess().next();
			state.setResults(_List.list(value).iterator());

			Iterator<T> results = transformer.apply(value).iterator();
			state.setValuesToProcess(Iterators.concat(state.getValuesToProcess(), results));
		}
	}
}