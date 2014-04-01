package org.vault.base.collections;

import java.util.Collection;

import org.vault.base.observer.Listenable;
import org.vault.base.observer.ListenerRegistry;
import org.vault.base.observer.Listeners;
import org.vault.base.utilities.function.Operation.Operation_V1;

public class ListenableCollection<T> extends AbstractCollection<T> implements Listenable<Collection<T>> {
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
		listeners.apply(new Operation_V1<CollectionListener<T>>() {
			@Override
			public void apply(CollectionListener<T> first) {
				first.onAdd(element);
			}
		});

		return this.getBackingCollection().add(element);
	}

	@Override
	public boolean remove(final Object o) {
		listeners.apply(new Operation_V1<CollectionListener<T>>() {
			@Override
			public void apply(CollectionListener<T> first) {
				first.onRemove(o);
			}
		});

		return this.getBackingCollection().remove(o);
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		listeners.apply(new Operation_V1<CollectionListener<T>>() {
			@Override
			public void apply(CollectionListener<T> first) {
				for (T element : c) {
					first.onAdd(element);
				}
			}
		});

		return this.addAll(c);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		listeners.apply(new Operation_V1<CollectionListener<T>>() {
			@Override
			public void apply(CollectionListener<T> first) {
				for (Object element : c) {
					first.onRemove(element);
				}
			}
		});

		return this.getBackingCollection().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO implement me
		return this.getBackingCollection().retainAll(c);
	}

	@Override
	public void clear() {
		this.getBackingCollection().clear();
	}
}