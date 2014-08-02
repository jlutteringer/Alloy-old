package org.alloy.metal.collections.map;

import java.util.HashMap;
import java.util.function.Supplier;

public class DefaultHashMap<K, V> extends HashMap<K, V> {
	private static final long serialVersionUID = -4762508189017754826L;

	protected transient Supplier<V> supplier;

	public DefaultHashMap(Supplier<V> supplier) {
		this.supplier = supplier;
	}

	@SuppressWarnings("unchecked")
	@Override
	public V get(Object k) {
		if (!this.containsKey(k) && supplier != null) {
			this.put((K) k, this.supplier.get());
		}
		return super.get(k);
	}
}