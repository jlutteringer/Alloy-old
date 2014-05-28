package org.vault.base.domain;

import java.util.Comparator;

public interface Orderable {
	public int getOrder();

	public static Comparator<Orderable> comparator() {
		return (first, second) -> Integer.compare(first.getOrder(), second.getOrder());
	}
}
