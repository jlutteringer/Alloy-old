package org.alloy.metal.collections;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class _Iterator {
	public static <T> T next(Iterator<T> iterator, Predicate<? super T> filter) throws NoSuchElementException {
		while (iterator.hasNext()) {
			T value = iterator.next();
			if (filter.test(value)) {
				return value;
			}
		}

		throw new NoSuchElementException();
	}
}
