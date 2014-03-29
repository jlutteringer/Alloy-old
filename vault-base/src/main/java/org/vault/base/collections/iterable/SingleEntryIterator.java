package org.vault.base.collections.iterable;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class SingleEntryIterator<T> implements Iterator<T> {
	private boolean nextGenerated = false;
	private T next;

	public boolean hasNext() {
		if (!nextGenerated) {
			try {
				next = generateNext();
				nextGenerated = true;
			} catch (NoSuchElementException e) {
				nextGenerated = false;
			}
		}
		return nextGenerated;
	}

	public T next() {
		// JOHN Auto-generated method stub
		return null;
	}

	protected abstract T generateNext() throws NoSuchElementException;
}
