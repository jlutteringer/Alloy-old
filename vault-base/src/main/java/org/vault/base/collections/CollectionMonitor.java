package org.vault.base.collections;

import java.util.Collection;

import org.vault.base.collections.iterable.VIterables;
import org.vault.base.utilities.function.Condition;

import com.google.common.collect.Lists;

public class CollectionMonitor<T> {
	private Collection<T> collectionToMonitor;

	public CollectionMonitor(Collection<T> collectionToMonitor) {
		this.collectionToMonitor = collectionToMonitor;
	}

	public Condition hasChanged() {
		return new Condition() {
			private Collection<T> previousCollection = Lists.newArrayList();

			@Override
			public boolean test() {
				boolean hasChanged = true;
				if (VIterables.compareElements(previousCollection, collectionToMonitor, false)) {
					hasChanged = false;
				}

				previousCollection = Lists.newArrayList(collectionToMonitor);
				return hasChanged;
			}
		};
	}
}