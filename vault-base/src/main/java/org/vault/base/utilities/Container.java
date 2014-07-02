package org.vault.base.utilities;

public class Container<T> {
	private T value;

	public T getValue() {
		if (value == null) {
			throw new IllegalStateException("Get value cannot be null");
		}
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public boolean isEmpty() {
		if (value == null) {
			return true;
		}
		return false;
	}

	public static <T> Container<T> empty() {
		return new Container<T>();
	}
}
