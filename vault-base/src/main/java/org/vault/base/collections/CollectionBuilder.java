package org.vault.base.collections;

import java.util.Arrays;
import java.util.Collection;

public class CollectionBuilder<T extends Collection<N>, N> extends BackedCollection<N> {
	private T collection;

	public CollectionBuilder(T collection) {
		this.collection = collection;
	}

	@SuppressWarnings("unchecked")
	public CollectionBuilder<T, N> add(N... items) {
		collection.addAll(Arrays.asList(items));
		return this;
	}

	@Override
	public Collection<N> getBackingCollection() {
		return collection;
	}

	public T unwrap() {
		return collection;
	}
}