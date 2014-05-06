package org.vault.base.enumeration;

import java.util.Map;

import org.vault.base.collections.map.VMaps;

import com.google.common.base.Throwables;

public class VEnumerations {
	private static final Map<Class<?>, Map<String, VEnumeration>> TYPES = VMaps.defaultHashMap(VMaps.mapSupplier());

	public static <T extends AbstractVEnumeration> T create(String type, String friendlyType, Class<T> clazz) {
		try {
			T newEnumeration = clazz.newInstance();
			newEnumeration.setType(type);
			newEnumeration.setFriendlyType(friendlyType);

			TYPES.get(clazz).put(type, newEnumeration);
			return newEnumeration;
		} catch (InstantiationException | IllegalAccessException e) {
			throw Throwables.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends VEnumeration> T getInstance(String type, Class<T> clazz) {
		return (T) TYPES.get(clazz).get(type);
	}
}
