package org.vault.base.collections;

import java.util.Collection;

public class V_Collections {
	public static <T> Collection<T> listenableCollection(Collection<T> target, CollectionListener<T> listener) {
		ListenableCollection<T> listenableCollection = new ListenableCollection<>();
		listenableCollection.setCollection(target);
		listenableCollection.getListeners().add(listener);
		return listenableCollection;
	}
}