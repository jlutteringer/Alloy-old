package org.alloy.metal.function.equality;

import java.util.Map;

import org.alloy.metal.function.Groups;
import org.alloy.metal.function.Option;
import org.alloy.metal.function._Tuple;
import org.alloy.metal.function.Groups.BiGroup;
import org.alloy.metal.function.Tuple.Pair;
import org.alloy.metal.object._Object;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;

public class EqualityMapping<T> {
	private static final Logger logger = LogManager.getLogger(EqualityMapping.class);
	private Map<BiGroup<Object>, Pair<BiGroup<Object>, AsymmetricEqualitor<Object, Object>>> backingMap = Maps.newHashMap();

	public boolean test(T first, T second) {
		logger.printf(Level.TRACE, "Testing equality of objects [%s] [%s]", first, second);
		Option<Boolean> baseTest = _Object.baseEquals(first, second);
		if (baseTest.isPresent()) {
			return baseTest.get();
		}

		BiGroup<Class<?>> key = Groups.group(first.getClass(), second.getClass());
		if (backingMap.containsKey(key)) {
			logger.trace("Found registered test - running test");
			Pair<BiGroup<Object>, AsymmetricEqualitor<Object, Object>> context = backingMap.get(key);
			BiGroup<Object> contextGroup = context.getFirst();

			AsymmetricEqualitor<Object, Object> test = context.getSecond();
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

	public <A extends T> void addEquality(Class<A> type, SymmetricEqualitor<A> test) {
		this.addEquality(type, type, test);
	}

	@SuppressWarnings("unchecked")
	public <A extends T, B extends T> void addEquality(Class<A> firstType, Class<B> secondType, AsymmetricEqualitor<A, B> test) {
		BiGroup<Object> key = Groups.group(firstType, secondType);
		backingMap.put(key, _Tuple.of(key, (AsymmetricEqualitor<Object, Object>) test));
	}
}