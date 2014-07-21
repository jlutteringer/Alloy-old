package org.vault.base.utilities.object;

import org.vault.base.utilities.VOptional;

public class VObjects {
	public static VOptional<Boolean> baseEquals(Object first, Object second) {
		return baseEquals(first, second, false);
	}

	public static VOptional<Boolean> baseEquals(Object first, Object second, boolean checkType) {
		if (first == second) {
			return VOptional.of(true);
		}
		if (second == null) {
			return VOptional.of(false);
		}

		if (checkType) {
			if (!first.getClass().isAssignableFrom(second.getClass())) {
				return VOptional.of(false);
			}
		}

		return VOptional.empty();
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
