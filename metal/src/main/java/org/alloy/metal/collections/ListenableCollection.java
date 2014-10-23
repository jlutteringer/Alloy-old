package org.alloy.metal.collections;

import java.util.Collection;
import java.util.function.Consumer;

import org.alloy.metal.listener.ListenerContext;
import org.alloy.metal.utilities._Exception;

import com.google.common.collect.ForwardingCollection;

public class ListenableCollection<T> extends ForwardingCollection<T> {
	private ListenerContext<CollectionOperation, T> listeners = new ListenerContext<>();
	private final Collection<T> delegate;

	public ListenableCollection(Collection<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	protected Collection<T> delegate() {
		return delegate;
	}

	@Override
	public boolean add(final T element) {
		listeners.apply(CollectionOperation.ADD, element);
		return this.delegate().add(element);
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean remove(final Object o) {
		_Exception.ignore(() -> listeners.apply(CollectionOperation.REMOVE, (T) o));
		return this.delegate().remove(o);
	}

	@Override
	public boolean addAll(final Collection<? extends T> c) {
		c.forEach((element) -> listeners.apply(CollectionOperation.ADD, element));
		return this.addAll(c);
	}

	@Override
	public boolean removeAll(final Collection<?> c) {
		c.forEach((element) -> _Exception.ignore(() -> listeners.apply(CollectionOperation.REMOVE, (T) element)));
		return this.delegate().removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// FUTURE
		return this.delegate().retainAll(c);
	}

	@Override
	public void clear() {
		this.forEach((element) -> listeners.apply(CollectionOperation.REMOVE, element));
		this.delegate().clear();
	}

	public enum CollectionOperation {
		ADD, REMOVE
	}

	public void addListener(CollectionOperation operation, Consumer<T> listener) {
		listeners.put(operation, listener);
	}
}