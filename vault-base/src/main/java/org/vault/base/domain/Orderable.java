package org.vault.base.domain;

import java.util.Comparator;

public interface Orderable extends Comparable<Orderable> {
	public int getOrder();

	@Override
	public default int compareTo(Orderable o) {
		return comparator().compare(this, o);
	}

	public static Comparator<Orderable> comparator() {
		return (first, second) -> Integer.compare(first.getOrder(), second.getOrder());
	}
}
