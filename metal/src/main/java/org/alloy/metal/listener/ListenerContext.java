package org.alloy.metal.listener;

import java.util.function.Consumer;

import org.alloy.metal.collections.map._Map;

import com.google.common.collect.Multimap;

public class ListenerContext<T, N> {
	private Multimap<T, Consumer<N>> internalMap = _Map.newMultiMap();

	public void apply(T key, N value) {
		internalMap.get(key).forEach((element) -> element.accept(value));
	}

	public void put(T key, Consumer<N> value) {
		internalMap.put(key, value);
	}
}