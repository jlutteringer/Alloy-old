package org.alloy.metal.enumeration;

import java.util.List;
import java.util.Map;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.collections.map._Map;

import com.google.common.base.Throwables;

public class _ExtendableEnumeration {
	private static boolean configured = false;
	private static Map<String, List<ExtendableEnumeration>> enumerations = _Map.defaultHashMap(_List.listSupplier());

	public static boolean isConfigured() {
		return configured;
	}

	public static void endConfiguration() {
		configured = true;
	}

	public static <T extends AbstractExtendableEnumeration> T create(String type, String friendlyType, Class<T> clazz) {
		if (configured) {
			throw new RuntimeException("Configuration of enumerations has already been completed");
		}

		try {
			T newEnumeration = clazz.newInstance();
			newEnumeration.setType(type);
			newEnumeration.setFriendlyType(friendlyType);
			enumerations.get(type).add(newEnumeration);

			return newEnumeration;
		} catch (InstantiationException | IllegalAccessException e) {
			throw Throwables.propagate(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends ExtendableEnumeration> T getInstance(String type, Class<T> clazz) {
		if (!configured) {
			throw new RuntimeException("Configuration of enumerations has not been completed");
		}

		if (enumerations.containsKey(type)) {
			try {
				return (T) _Iterable.filterSingleResult(enumerations.get(type),
						(enumeration) -> clazz.isAssignableFrom(enumeration.getClass()));
			} catch (Exception e) {
				throw new RuntimeException("Error finding matching enumeration for type: " + type + " and class " + clazz, e);
			}
		}

		throw new RuntimeException("No matching enumeration for type: " + type + " and class " + clazz);
	}
}