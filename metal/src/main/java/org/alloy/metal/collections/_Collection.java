package org.alloy.metal.collections;

import java.util.Collection;

public class _Collection {
	public static <T> Collection<T> listenableCollection(Collection<T> target, CollectionListener<T> listener) {
		ListenableCollection<T> listenableCollection = new ListenableCollection<>();
		listenableCollection.setCollection(target);
		listenableCollection.getListeners().add(listener);
		return listenableCollection;
	}

	public static <T> CollectionMonitor<T> monitor(Collection<T> collection) {
		return new CollectionMonitor<T>(collection);
	}
}