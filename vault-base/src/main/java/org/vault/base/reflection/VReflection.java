package org.vault.base.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vault.base.collections.lists.VLists;
import org.vault.base.utilities.exception.Exceptions;
import org.vault.base.utilities.function.VPredicates;
import org.vault.base.utilities.matcher.Selectors;

import com.google.common.collect.Lists;

public class VReflection {
	private static final Logger logger = LogManager.getLogger(VReflection.class);

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

	public static List<Class<?>> getHierarchy(Class<?> clazz, Predicate<Class<?>> filter) {
		List<Class<?>> hierarchy = Lists.newArrayList();
		Class<?> currentClazz = clazz;

		while (currentClazz.getSuperclass() != null) {
			currentClazz = currentClazz.getSuperclass();
			if (filter.test(currentClazz) && currentClazz != Object.class) {
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
			classes = Selectors.getSelector(ClassType.classTypeMatcher(ClassType.CONCRETE)).getMatches(classes);
		}

		for (Class<T> clazz : classes) {
			concreteInstances.add(construct(clazz, args));
		}

		return concreteInstances;
	}

	@SuppressWarnings("unchecked")
	public static <T> T invoke(Object object, String methodName, Class<T> returnType, Object... arguments) {
		return Exceptions.propagate(() -> {
			return (T) getClassMethod(true, object.getClass(), methodName, VClasses.getTypes(arguments))
					.orElseThrow(() -> new NoSuchMethodException(methodName))
					.invoke(object, arguments);
		});
	}

	@SuppressWarnings("unchecked")
	public static <T> T getField(Object object, String fieldName, Class<T> clazz) {
		return Exceptions.propagate(() -> {
			return (T) getClassFieldByName(true, object.getClass(), fieldName)
					.orElseThrow(() -> new NoSuchFieldException(fieldName))
					.get(object);
		});
	}

	@SuppressWarnings("unchecked")
	public static <T, N> Map<T, N> getMap(Object object, String fieldName, Class<T> clazz1, Class<N> clazz2) {
		return getField(object, fieldName, Map.class);
	}

	public static Optional<Method> getClassMethod(boolean makeAccessable, Class<?> clazz, String methodName, List<Class<?>> argumentTypess) {
		Optional<Class<?>> matchedClass = traverseObjectGraph(clazz, methodClassMatcher(methodName, argumentTypess));
		if (matchedClass.isPresent()) {
			Optional<Method> method = getMethodFromClass(matchedClass.get(), methodName, argumentTypess);
			method.ifPresent((param) -> {
				if (!param.isAccessible() && makeAccessable) {
					param.setAccessible(true);
				}
			});

			return method;
		}

		return Optional.empty();
	}

	public static Optional<Field> getClassFieldByName(boolean makeAccessable, Class<?> clazz, String fieldName) {
		Optional<Class<?>> matchedClass = traverseObjectGraph(clazz, fieldNameClassMatcher(fieldName));
		if (matchedClass.isPresent()) {
			Optional<Field> field = getFieldFromClassByName(matchedClass.get(), fieldName);
			field.ifPresent((param) -> {
				if (!param.isAccessible() && makeAccessable) {
					param.setAccessible(true);
				}
			});

			return field;
		}
		return Optional.empty();
	}

	public static Predicate<Class<?>> fieldNameClassMatcher(String fieldName) {
		return (clazz) -> {
			if (getFieldFromClassByName(clazz, fieldName).isPresent()) {
				return true;
			}
			return false;
		};
	}

	public static Predicate<Class<?>> methodClassMatcher(String methodName, List<Class<?>> argumentTypess) {
		return (clazz) -> {
			if (getMethodFromClass(clazz, methodName, argumentTypess).isPresent()) {
				return true;
			}
			return false;
		};
	}

	private static Optional<Class<?>> traverseObjectGraph(Class<?> clazz, Predicate<Class<?>> matcher) {
		Class<?> currentClass = clazz;
		boolean found = false;

		while (currentClass != null && !found) {
			if (matcher.test(currentClass)) {
				found = true;
				break;
			}

			currentClass = currentClass.getSuperclass();
		}

		return Optional.ofNullable(currentClass);
	}

	private static Optional<Method> getMethodFromClass(Class<?> clazz, String methodName, List<Class<?>> argumentTypes) {
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method declaredMethod : declaredMethods) {
			Predicate<Method> criteria =
					VPredicates.and(
							(method) -> method.getName().equals(methodName),
							(method) -> {
								if (method.getParameterTypes().length != argumentTypes.size()) {
									return false;
								}

								for (int count = 0; count < method.getParameterTypes().length; count++) {
									if (!method.getParameterTypes()[count].isAssignableFrom(argumentTypes.get(count))) {
										return false;
									}
								}
								return true;
							});

			if (criteria.test(declaredMethod)) {
				logger.trace(
						"Found method [" + methodName + "] for class [" + clazz.getSimpleName() + "] with args [" +
								VLists.transform(argumentTypes, VClasses.stringify()) + "]");

				return Optional.of(declaredMethod);
			}
		}

		logger.trace(
				"Couldn't find method [" + methodName + "] for class [" + clazz.getSimpleName() + "] with args [" +
						VLists.transform(argumentTypes, VClasses.stringify()) + "]");

		return Optional.empty();
	}

	private static Optional<Field> getFieldFromClassByName(Class<?> clazz, String fieldName) {
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field declaredField : declaredFields) {
			if (fieldName.equals(declaredField.getName())) {
				return Optional.of(declaredField);
			}
		}
		return Optional.empty();
	}
}