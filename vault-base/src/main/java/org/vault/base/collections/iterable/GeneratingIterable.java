package org.vault.base.collections.iterable;

import java.util.Iterator;

import org.vault.base.utilities.function.Generator;

public class GeneratingIterable<T> extends IterableWithToString<T> {

	private Generator<Iterator<T>> generator;

	public GeneratingIterable(Generator<Iterator<T>> generator) {
		this.generator = generator;
	}

	public Iterator<T> iterator() {
		return generator.apply();
	}
}
