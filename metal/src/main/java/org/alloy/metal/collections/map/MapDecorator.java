package org.alloy.metal.collections.map;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

public abstract class MapDecorator<T, N> extends AbstractMap<T, N> {
	protected Map<T, N> internalMap;

	public MapDecorator(Map<T, N> internalMap) {
		this.internalMap = internalMap;
	}

	@Override
	public Set<Entry<T, N>> entrySet() {
		return internalMap.entrySet();
	}

	@Override
	public N put(T key, N value) {
		return internalMap.put(key, value);
	}

	@Override
	public N get(Object key) {
		return internalMap.get(key);
	}
}
