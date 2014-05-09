package org.vault.base.enumeration;

import java.util.Map;

import org.vault.base.collections.map.VMaps;

import com.google.common.base.Throwables;

//TODO this class needs some thought on how we handle static initializations
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
		boolean exists = TYPES.get(clazz).containsKey(type);
		if (!exists) {
			try {
				Class.forName(clazz.getName());
			} catch (ClassNotFoundException e) {
				// Do nothing
			}

			exists = TYPES.get(clazz).containsKey(type);
		}

		if (!exists) {
			throw new RuntimeException("No VEnumeration found for type " + type + " and class " + clazz);
		}

		return (T) TYPES.get(clazz).get(type);
	}
}
