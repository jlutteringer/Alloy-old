package org.vault.base.collections.iterable;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.vault.base.collections.iterable.IteratorSupplierContext.IteratorSupplierState;
import org.vault.base.utilities.Value;
import org.vault.base.utilities.function.StatefulSupplier;
import org.vault.base.utilities.function.SupplierContext;
import org.vault.base.utilities.function.VFunctions;
import org.vault.base.utilities.function.VPredicates;

public class IteratorSupplierContext<T, N> implements SupplierContext<IteratorSupplierState<T, N>, Value<N>> {
	private Iterable<T> iterable;

	private Predicate<? super N> filter = VPredicates.matchAll();
	private Function<T, Iterator<N>> transformer = VIterables.singletonIteratorTransformer(VFunctions.cast());

	public IteratorSupplierContext(Iterable<T> iterable) {
		this.iterable = iterable;
	}

	@Override
	public StatefulSupplier<IteratorSupplierState<T, N>, Value<N>> getPrimarySupplier() {
		return (state) -> {
			return this.checkPrimary(state);
		};
	}

	private Value<N> checkPrimary(IteratorSupplierState<T, N> state) {
		boolean finished = false;
		while (!finished) {
			Value<N> value = checkSecondary(state);
			if (value.isDefined()) {
				return value;
			}

			if (state.primaryIterator().hasNext()) {
				T primaryValue = state.primaryIterator().next();
				Iterator<N> secondaryIterator = transformer.apply(primaryValue);
				state.setSecondaryIterator(secondaryIterator);
			}
			else {
				finished = true;
			}
		}

		return Value.none();
	}

	private Value<N> checkSecondary(IteratorSupplierState<T, N> state) {
		boolean finished = false;
		if (state.secondaryIterator() != null) {
			while (!finished) {
				if (state.secondaryIterator().hasNext()) {
					N value = state.secondaryIterator().next();
					if (filter.test(value)) {
						return Value.of(value);
					}
				}
				else {
					state.clearSecondaryIterator();
					finished = true;
				}
			}
		}

		return Value.none();
	}

	@Override
	public Supplier<IteratorSupplierState<T, N>> getStateSupplier() {
		return () -> {
			return new IteratorSupplierState<T, N>(iterable.iterator());
		};
	}

	public Predicate<? super N> getFilter() {
		return filter;
	}

	public void setFilter(Predicate<? super N> filter) {
		this.filter = filter;
	}

	public Function<T, Iterator<N>> getTransformer() {
		return transformer;
	}

	public void setTransformer(Function<T, Iterator<N>> transformer) {
		this.transformer = transformer;
	}

	public static class IteratorSupplierState<T, N> {
		private Iterator<? extends T> primaryIterator;
		private Iterator<N> secondaryIterator;

		public IteratorSupplierState(Iterator<? extends T> primaryIterator) {
			this.primaryIterator = primaryIterator;
		}

		public void clearSecondaryIterator() {
			secondaryIterator = null;
		}

		public Iterator<? extends T> primaryIterator() {
			return primaryIterator;
		}

		public Iterator<N> secondaryIterator() {
			return secondaryIterator;
		}

		public void setSecondaryIterator(Iterator<N> secondaryIterator) {
			this.secondaryIterator = secondaryIterator;
		}
	}
}