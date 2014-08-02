package org.alloy.metal.object;

import org.alloy.metal.function.Option;

public class _Object {
	public static Option<Boolean> baseEquals(Object first, Object second) {
		return baseEquals(first, second, false);
	}

	public static Option<Boolean> baseEquals(Object first, Object second, boolean checkType) {
		if (first == second) {
			return Option.of(true);
		}
		if (second == null) {
			return Option.of(false);
		}

		if (checkType) {
			if (!first.getClass().isAssignableFrom(second.getClass())) {
				return Option.of(false);
			}
		}

		return Option.empty();
	}

	public static int hashCode(Object... fields) {
		return hashCode(1, fields);
	}

	public static int hashCode(int seed, Object... fields) {
		final int prime = 31;
		int result = seed;
		for (Object field : fields) {
			result = prime * result + ((field == null) ? 0 : field.hashCode());
		}
		return result;
	}

	public static boolean equalIfPresent(Object first, Object second) {
		if (first != null && second != null) {
			return first.equals(second);
		}
		return true;
	}
}
