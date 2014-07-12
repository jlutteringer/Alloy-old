package org.vault.base.reflection;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import com.google.common.collect.Lists;

public class VClasses {
	public static void intialize(Class<?> clazz) {
		try {
			Class.forName(clazz.getName());
		} catch (ClassNotFoundException e) {
			// Do nothing
		}
	}

	public static List<Class<?>> getTypes(Object... objects) {
		return getTypes(Arrays.asList(objects));
	}

	public static List<Class<?>> getTypes(List<Object> objects) {
		List<Class<?>> types = Lists.newArrayList();
		objects.forEach((object) -> types.add(object.getClass()));
		return types;
	}

	public static Function<Class<?>, String> stringify() {
		return (clazz) -> clazz.getName();
	}
}