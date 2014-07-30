package org.vault.base.utilities.function;

public class Equalitors {
	public static <T> Equalitor<T> notEqual() {
		return (first, second) -> false;
	}
}
