package org.vault.base.equality;

import java.util.Map;
import java.util.function.BiFunction;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vault.base.utilities.VOptional;
import org.vault.base.utilities.object.VObjects;
import org.vault.base.utilities.tuple.Groups;
import org.vault.base.utilities.tuple.Groups.BiGroup;
import org.vault.base.utilities.tuple.Tuple.Pair;
import org.vault.base.utilities.tuple.Tuples;

import com.google.common.collect.Maps;

public class EqualityContext<T> {
	private static final Logger logger = LogManager.getLogger(EqualityContext.class);
	private Map<BiGroup<Object>, Pair<BiGroup<Object>, EqualityTest<Object, Object>>> backingMap = Maps.newHashMap();

	public boolean test(T first, T second) {
		logger.printf(Level.TRACE, "Testing equality of objects [%s] [%s]", first, second);
		VOptional<Boolean> baseTest = VObjects.baseEquals(first, second);
		if (baseTest.isPresent()) {
			return baseTest.get();
		}

		BiGroup<Class<?>> key = Groups.group(first.getClass(), second.getClass());
		if (backingMap.containsKey(key)) {
			logger.trace("Found registered test - running test");
			Pair<BiGroup<Object>, EqualityTest<Object, Object>> context = backingMap.get(key);
			BiGroup<Object> contextGroup = context.getFirst();

			EqualityTest<Object, Object> test = context.getSecond();
			boolean result;
			if (first.getClass().equals(contextGroup.getFirst()) && second.getClass().equals(contextGroup.getSecond())) {
				result = test.apply(first, second);
			}
			else {
				result = test.apply(second, first);
			}

			logger.printf(Level.TRACE, "Calculated result of [%s] for objects [%s] [%s]", result, first, second);
			return result;
		}
		else {
			logger.trace("Falling back to .equals()");
			return first.equals(second);
		}
	}

	public <A extends T> void addEquality(Class<A> type, EqualityTest<A, A> test) {
		this.addEquality(type, type, test);
	}

	@SuppressWarnings("unchecked")
	public <A extends T, B extends T> void addEquality(Class<A> firstType, Class<B> secondType, EqualityTest<A, B> test) {
		BiGroup<Object> key = Groups.group(firstType, secondType);
		backingMap.put(key, Tuples.of(key, (EqualityTest<Object, Object>) test));
	}

	public static interface EqualityTest<T, N> extends BiFunction<T, N, Boolean> {

	}
}