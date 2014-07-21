package org.vault.base.collections;

import java.util.Collection;
import java.util.Iterator;

public abstract class BackedCollection<T> implements Collection<T> {
	public abstract Collection<T> getBackingCollection();

	@Override
	public int size() {
		return this.getBackingCollection().size();
	}

	@Override
	public boolean isEmpty() {
		return this.getBackingCollection().isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return this.getBackingCollection().contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return this.getBackingCollection().iterator();
	}

	@SuppressWarnings("hiding")
	@Override
	public <T> T[] toArray(T[] a) {
		return this.getBackingCollection().toArray(a);
	}

	@Override
	public boolean add(T e) {
		return this.getBackingCollection().add(e);
	}

	@Override
	public boolean remove(Object o) {
		return this.getBackingCollection().remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.getBackingCollection().containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return this.getBackingCollection().addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return this.getBackingCollection().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.getBackingCollection().retainAll(c);
	}

	@Override
	public void clear() {
		this.getBackingCollection().clear();
	}

	@Override
	public Object[] toArray() {
		return this.getBackingCollection().toArray();
	}
}
