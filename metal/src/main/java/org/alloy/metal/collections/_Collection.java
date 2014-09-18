package org.alloy.metal.collections;

import java.util.Collection;

public class _Collection {
	public static <T> ListenableCollection<T> listenableCollection(Collection<T> target) {
		ListenableCollection<T> listenableCollection = new ListenableCollection<>(target);
		return listenableCollection;
	}

	public static <T> CollectionMonitor<T> monitor(Collection<T> collection) {
		return new CollectionMonitor<T>(collection);
	}
}