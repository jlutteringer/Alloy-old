package org.vault.base.collections.map;

import java.util.Map;

public class FieldMap extends TypeMap<String> {
	private static final long serialVersionUID = 5525329181370458320L;

	public FieldMap(Map<String, Object> internalMap) {
		super(internalMap);
	}
}
