package org.vault.base.collections.iterable;

import java.util.Iterator;

import org.vault.base.utilities.function.Generator;

public class V_Iterables {
	public static <T> Iterable<T> createFromIteratorGenerator(Generator<Iterator<T>> generator) {
		return new GeneratingIterable<T>(generator);
	}
	
	public static <T> Iterable<T> createFromElementGenerator(Generator<T> generator) {
		return V_Iterables.createFromIteratorGenerator(V_Iterables.createIteratorGeneratorFromElementGenerator(generator));
	}
	
	private static <T> Generator<Iterator<T>> createIteratorGeneratorFromElementGenerator(final Generator<T> generator) {
		return new Generator<Iterator<T>>() {
			public Iterator<T> apply() {
				return new GeneratingIterator<T>(generator);
			}
		};
	}
}
