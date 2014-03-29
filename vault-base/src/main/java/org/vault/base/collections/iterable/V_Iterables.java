package org.vault.base.collections.iterable;

import java.util.Iterator;

import org.vault.base.utilities.function.Generator;

public class V_Iterables {
	public static <T> Iterable<T> create(Generator<Iterator<T>> generator) {
		return new IterableWithGenerator<T>(generator);
	}
}
