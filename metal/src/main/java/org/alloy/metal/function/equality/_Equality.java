package org.alloy.metal.function.equality;


public class _Equality {
	public static <T> SymmetricEqualitor<T> notEqual() {
		return (first, second) -> false;
	}
}
