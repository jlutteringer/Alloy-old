package org.vault.base.collections;

import java.util.Arrays;
import java.util.Collection;

public class CollectionMonad<T extends Collection<N>, N> {
	private T collection;

	public CollectionMonad(T collection) {
		this.collection = collection;
	}

	@SuppressWarnings("unchecked")
	public CollectionMonad<T, N> add(N... items) {
		collection.addAll(Arrays.asList(items));
		return this;
	}

	public T unwrap() {
		return collection;
	}
}