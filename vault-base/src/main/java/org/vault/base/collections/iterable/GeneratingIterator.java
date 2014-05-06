package org.vault.base.collections.iterable;

import java.util.NoSuchElementException;

import org.vault.base.utilities.Value;
import org.vault.base.utilities.function.StatefulSupplier;

public class GeneratingIterator<T, N> extends SingleEntryIterator<T> {
	private StatefulSupplier<N, Value<T>> supplier;
	private N state;

	public GeneratingIterator(StatefulSupplier<N, Value<T>> supplier, N state) {
		this.supplier = supplier;
		this.state = state;
	}

	@Override
	protected T generateNext() throws NoSuchElementException {
		return supplier.apply(state).getOrThrow(new NoSuchElementException());
	}
}
