package org.vault.base.reflection;

import java.lang.reflect.Modifier;
import java.util.Collection;

import org.vault.base.collections.lists.VLists;
import org.vault.base.utilities.matcher.Matcher;

public enum ClassType {
	CONCRETE, ABSTRACT, INTERFACE;

	public static Matcher<Class<?>> classTypeMatcher(ClassType... types) {
		return classTypeMatcher(VLists.list(types));
	}

	public static Matcher<Class<?>> classTypeMatcher(Collection<ClassType> types) {
		return (clazz) -> {
			if (types.contains(INTERFACE) && clazz.isInterface()) {
				return true;
			}

			if (types.contains(ABSTRACT) && Modifier.isAbstract(clazz.getModifiers())) {
				return true;
			}

			if (types.contains(CONCRETE) && !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers())) {
				return true;
			}

			return false;
		};
	}
}
