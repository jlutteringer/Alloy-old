package org.vault.base.domain;

import java.util.Comparator;

import org.springframework.core.Ordered;

public interface Orderable extends Comparable<Orderable>, Ordered {
	@Override
	public default int compareTo(Orderable o) {
		return comparator().compare(this, o);
	}

	public static Comparator<Orderable> comparator() {
		return (first, second) -> Integer.compare(first.getOrder(), second.getOrder());
	}
}
