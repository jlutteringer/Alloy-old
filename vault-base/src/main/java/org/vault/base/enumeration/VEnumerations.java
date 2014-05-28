package org.vault.base.enumeration;

import java.util.List;

import com.google.common.base.Throwables;

//TODO this class needs some thought on how we handle static initializations
public class VEnumerations {
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

	public static <T extends AbstractVEnumeration> T create(String type, String friendlyType, Class<T> clazz, List<T> valueList) {
		T vEnum = create(type, friendlyType, clazz);
		valueList.add(vEnum);
		return vEnum;
	}
}
