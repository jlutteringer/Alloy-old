package org.alloy.metal.collections;

import java.util.Arrays;
import java.util.Collection;

import com.google.common.collect.ForwardingCollection;

public class CollectionBuilder<T extends Collection<N>, N> extends ForwardingCollection<N> {
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
	protected Collection<N> delegate() {
		return collection;
	}

	public T unwrap() {
		return collection;
	}
}