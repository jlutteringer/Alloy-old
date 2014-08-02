package org.vault.base.reflection;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.function.Predicate;

import org.vault.base.collections.lists._Lists;

public enum ClassType {
	CONCRETE, ABSTRACT, INTERFACE;

	public static Predicate<Class<?>> classTypeMatcher(ClassType... types) {
		return classTypeMatcher(_Lists.list(types));
	}

	public static Predicate<Class<?>> classTypeMatcher(Collection<ClassType> types) {
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
