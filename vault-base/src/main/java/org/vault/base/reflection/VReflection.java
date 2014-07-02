package org.vault.base.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vault.base.utilities.Container;
import org.vault.base.utilities.exception.Exceptions;
import org.vault.base.utilities.matcher.Matcher;
import org.vault.base.utilities.matcher.Matchers;

import com.google.common.collect.Lists;

public class VReflection {
	public static Class<?> getClass(Type type) {
		if (type instanceof Class) {
			return (Class<?>) type;
		} else if (type instanceof ParameterizedType) {
			return getClass(((ParameterizedType) type).getRawType());
		} else if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type).getGenericComponentType();
			Class<?> componentClass = getClass(componentType);
			if (componentClass != null) {
				return Array.newInstance(componentClass, 0).getClass();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	 * Get the actual type arguments a child class has used to extend a generic
	 * base class.
	 * 
	 * @param baseClass
	 *            the base class
	 * @param childClass
	 *            the child class
	 * @return a list of the raw classes for the actual type arguments.
	 */
	public static <T> List<Class<?>> getTypeArguments(Class<?> baseClass, Class<?> childClass) {
		Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
		Type type = childClass;
		// start walking up the inheritance hierarchy until we hit baseClass
		while (!getClass(type).equals(baseClass)) {
			if (type instanceof Class) {
				// there is no useful information for us in raw types, so just
				// keep going.
				type = ((Class<?>) type).getGenericSuperclass();
			} else {
				ParameterizedType parameterizedType = (ParameterizedType) type;
				Class<?> rawType = (Class<?>) parameterizedType.getRawType();

				Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
				TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
				for (int i = 0; i < actualTypeArguments.length; i++) {
					resolvedTypes.put(typeParameters[i], actualTypeArguments[i]);
				}

				if (!rawType.equals(baseClass)) {
					type = rawType.getGenericSuperclass();
				}
			}
		}

		// finally, for each actual type argument provided to baseClass,
		// determine (if possible)
		// the raw class for that type argument.
		Type[] actualTypeArguments;
		if (type instanceof Class) {
			actualTypeArguments = ((Class<?>) type).getTypeParameters();
		} else {
			actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
		}
		List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
		// resolve types by chasing down type variables.
		for (Type baseType : actualTypeArguments) {
			while (resolvedTypes.containsKey(baseType)) {
				baseType = resolvedTypes.get(baseType);
			}
			typeArgumentsAsClasses.add(getClass(baseType));
		}
		return typeArgumentsAsClasses;
	}

	public static List<Class<?>> getHierarchy(Class<?> clazz, Matcher<Class<?>> filter) {
		List<Class<?>> hierarchy = Lists.newArrayList();
		Class<?> currentClazz = clazz;

		while (currentClazz.getSuperclass() != null) {
			currentClazz = currentClazz.getSuperclass();
			if (filter.matches(currentClazz) && currentClazz != Object.class) {
				hierarchy.add(currentClazz);
			}
		}
		return hierarchy;
	}

	@SuppressWarnings("unchecked")
	public static <T> T construct(Class<T> clazz, Object... args) {
		for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
			if (constructor.getParameterTypes().length == args.length) {
				constructor.setAccessible(true);

				try {
					return (T) constructor.newInstance(args);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					throw new RuntimeException(e);
				}
			}
		}

		throw new RuntimeException("No constructor for class " + clazz + " and args " + args);
	}

	public static <T> List<T> constructAll(List<Class<T>> unfliteredClasses, boolean filter, Object... args) {
		List<T> concreteInstances = Lists.newArrayList();

		Iterable<Class<T>> classes = unfliteredClasses;
		if (filter) {
			classes = Matchers.getSelector(ClassType.classTypeMatcher(ClassType.CONCRETE)).getMatches(classes);
		}

		for (Class<T> clazz : classes) {
			concreteInstances.add(construct(clazz, args));
		}

		return concreteInstances;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getField(Object object, String fieldName, Class<T> clazz) {
		Container<T> container = Container.empty();
		Exceptions.propagate(() -> {
			Field field = null;
			Class<?> currentClass = object.getClass();
			boolean found = false;

			while (field == null && currentClass != null && !found) {
				Field[] declaredFields = currentClass.getDeclaredFields();
				for (Field declaredField : declaredFields) {
					if (fieldName.equals(declaredField.getName())) {
						field = declaredField;
						found = true;
						break;
					}
				}

				currentClass = currentClass.getSuperclass();
			}

			if (!found) {
				throw new NoSuchFieldException(fieldName);
			}

			if (!field.isAccessible()) {
				field.setAccessible(true);
			}
			container.setValue((T) field.get(object));
		});

		return container.getValue();
	}

	@SuppressWarnings("unchecked")
	public static <T, N> Map<T, N> getMap(Object object, String fieldName, Class<T> clazz1, Class<N> clazz2) {
		return getField(object, fieldName, Map.class);
	}
}
