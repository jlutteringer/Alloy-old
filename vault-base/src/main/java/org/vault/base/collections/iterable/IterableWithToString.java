package org.vault.base.collections.iterable;

import com.google.common.collect.Iterables;

public abstract class IterableWithToString<T> implements Iterable<T> {
	@Override
	public String toString() {
		return Iterables.toString(this);
	}
}