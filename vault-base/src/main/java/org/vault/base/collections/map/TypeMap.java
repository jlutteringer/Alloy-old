package org.vault.base.collections.map;

import java.io.Serializable;
import java.util.Map;

public class TypeMap<T> extends MapDecorator<T, Object> implements Serializable {
	private static final long serialVersionUID = -7818563203000307989L;

	public TypeMap(Map<T, Object> internalMap) {
		super(internalMap);
	}

	@SuppressWarnings("unchecked")
	public <N> N get(T key, Class<N> clazz) {
		return (N) internalMap.get(key);
	}
}