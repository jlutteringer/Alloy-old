package org.vault.base.enumeration;

import java.util.List;
import java.util.Map;

import org.vault.base.collections.iterable._Iterable;
import org.vault.base.collections.lists._Lists;
import org.vault.base.collections.map._Map;
import org.vault.base.utilities.matcher.Selectors;

import com.google.common.base.Throwables;

public class VEnumerations {
	private static boolean configured = false;
	private static Map<String, List<AoEnumeration>> enumerations = _Map.defaultHashMap(_Lists.listSupplier());

	public static boolean isConfigured() {
		return configured;
	}

	public static void endConfiguration() {
		configured = true;
	}

	public static <T extends AbstractAoEnumeration> T create(String type, String friendlyType, Class<T> clazz) {
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
	public static <T extends AoEnumeration> T getInstance(String type, Class<T> clazz) {
		if (!configured) {
			throw new RuntimeException("Configuration of enumerations has not been completed");
		}

		if (enumerations.containsKey(type)) {
			try {
				return (T) _Iterable.getSingleResult(
						Selectors.getSelector((enumeration) -> clazz.isAssignableFrom(enumeration.getClass()))
								.getMatches(enumerations.get(type)));

			} catch (Exception e) {
				throw new RuntimeException("Error finding matching enumeration for type: " + type + " and class " + clazz, e);
			}
		}

		throw new RuntimeException("No matching enumeration for type: " + type + " and class " + clazz);
	}
}