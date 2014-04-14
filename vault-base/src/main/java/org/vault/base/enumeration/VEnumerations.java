package org.vault.base.enumeration;

import java.util.Map;

import com.google.common.base.Throwables;
import com.google.common.collect.Maps;

public class VEnumerations {
	private static final Map<Class<?>, Map<String, VEnumerations>> TYPES = Maps.newHashMap();

	public static <T extends AbstractVEnumeration> T create(String type, String friendlyType, Class<T> clazz) {
		try {
			T newEnumeration = clazz.newInstance();
			newEnumeration.setType(type);
			newEnumeration.setFriendlyType(friendlyType);
			return newEnumeration;
		} catch (InstantiationException | IllegalAccessException e) {
			throw Throwables.propagate(e);
		}
	}
}
