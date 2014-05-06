package org.vault.base.utilities.object;

import com.google.common.base.Optional;

public class VObjects {
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

	public static Optional<Boolean> baseEquals(Object first, Object second) {
		if (first == second) {
			return Optional.of(true);
		}
		if (second == null) {
			return Optional.of(false);
		}
		if (first.getClass() != second.getClass()) {
			return Optional.of(false);
		}

		return Optional.absent();
	}

	public static boolean equalIfPresent(Object first, Object second) {
		if (first != null && second != null) {
			return first.equals(second);
		}
		return true;
	}
}
