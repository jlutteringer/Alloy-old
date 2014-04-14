package org.vault.base.collections;

import java.util.Collection;

import com.google.common.collect.Iterables;

public class VCollections {
	public static <T> Collection<T> listenableCollection(Collection<T> target, CollectionListener<T> listener) {
		ListenableCollection<T> listenableCollection = new ListenableCollection<>();
		listenableCollection.setCollection(target);
		listenableCollection.getListeners().add(listener);
		return listenableCollection;
	}

	public static <T> T getSingleResult(Collection<T> collection) {
		return VCollections.getSingleResult(collection, false);
	}

	public static <T> T getSingleResult(Collection<T> collection, boolean allowEmpty) {
		if (collection.isEmpty() && !allowEmpty) {
			throw new RuntimeException("Empty collection passed to get single result with allow empty = false");
		}

		if (collection.size() > 1) {
			throw new RuntimeException("Multiple results passed to get single result");
		}

		return Iterables.getFirst(collection, null);
	}
}