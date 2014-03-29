package org.vault.base.collections.iterable;

import java.util.Iterator;

import org.vault.base.utilities.function.Generator;

public class IterableWithGenerator<T> extends IterableWithToString<T> {

	private Generator<Iterator<T>> generator;

	public IterableWithGenerator(Generator<Iterator<T>> generator) {
		this.generator = generator;
	}

	public Iterator<T> iterator() {
		return generator.apply();
	}
}
