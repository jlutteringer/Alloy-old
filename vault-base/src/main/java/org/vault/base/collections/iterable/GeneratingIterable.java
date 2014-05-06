package org.vault.base.collections.iterable;

import java.util.Iterator;
import java.util.function.Supplier;

public class GeneratingIterable<T> extends IterableWithToString<T> {

	private Supplier<Iterator<T>> supplier;

	public GeneratingIterable(Supplier<Iterator<T>> supplier) {
		this.supplier = supplier;
	}

	@Override
	public Iterator<T> iterator() {
		return supplier.get();
	}
}
