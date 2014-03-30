package org.vault.base.collections.iterable;

import java.util.NoSuchElementException;

import org.vault.base.utilities.function.Generator;

public class GeneratingIterator<T> extends SingleEntryIterator<T> {
	private Generator<T> generator;
	
	public GeneratingIterator(Generator<T> generator) {
		this.generator = generator;
	}
	
	@Override
	protected T generateNext() throws NoSuchElementException {
		return generator.apply();
	}
}
