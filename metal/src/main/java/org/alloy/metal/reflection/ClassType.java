package org.alloy.metal.reflection;

import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.function.Predicate;

import org.alloy.metal.collections.lists._List;

public enum ClassType {
	CONCRETE, ABSTRACT, INTERFACE;

	public static Predicate<Class<?>> classTypeFilter(ClassType... types) {
		return classTypeMatcher(_List.list(types));
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
