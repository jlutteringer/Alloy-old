package org.alloy.metal.utilities;

import java.util.function.Predicate;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.function._Predicate;

public class _Precondition {
	@SafeVarargs
	public static <T> void check(Predicate<T> filter, T... objects) {
		if (_List.list(_Iterable.filter(_List.list(objects), filter)).size() != objects.length) {
			throw new IllegalArgumentException();
		}
	}

	@SafeVarargs
	public static <T> void notNull(T... objects) {
		_Precondition.check(_Predicate.isDefined().negate(), objects);
	}
}