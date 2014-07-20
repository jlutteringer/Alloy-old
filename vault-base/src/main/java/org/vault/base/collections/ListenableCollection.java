package org.vault.base.collections;

import java.util.Collection;

import org.vault.base.observer.Listenable;
import org.vault.base.observer.ListenerRegistry;
import org.vault.base.observer.Listeners;

public class ListenableCollection<T> extends BackedCollection<T> implements Listenable<Collection<T>> {
	private ListenerRegistry<Collection<T>, CollectionListener<T>> listeners;
	private Collection<T> collection;

	public ListenableCollection() {
		listeners = Listeners.<Collection<T>, CollectionListener<T>> createRegistry(this);
	}

	@Override
	public Collection<T> getBackingCollection() {
		return collection;
	}

	public Collection<T> getCollection() {
		return collection;
	}

	public void setCollection(Collection<T> collection) {
		this.collection = collection;
	}

	@Override
	public ListenerRegistry<Collection<T>, CollectionListener<T>> getListeners() {
		return listeners;
	}

	@Override
	public boolean add(final T element) {
		listeners.apply((listener) -> listener.onAdd(element));
		return this.getBackingCollection().add(element);
	}

	@Override
	public boolean remove(final Object o) {
		listeners.apply((listener) -> listener.onRemove(o));
		return this.getBackingCollection().remove(o);
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		listeners.apply((listener) -> c.forEach((element) -> listener.onAdd(element)));
		return this.addAll(c);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		listeners.apply((listener) -> c.forEach((element) -> listener.onRemove(element)));
		return this.getBackingCollection().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// FUTURE
		return this.getBackingCollection().retainAll(c);
	}

	@Override
	public void clear() {
		this.getBackingCollection().clear();
	}
}