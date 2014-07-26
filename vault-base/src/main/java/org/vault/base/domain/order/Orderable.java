package org.vault.base.domain.order;

import java.util.Comparator;

import org.springframework.core.Ordered;

public interface Orderable extends Ordered {
	public static final int DEFAULT_ORDER = 10000;

	public static Comparator<Orderable> comparator() {
		return (first, second) -> {
			int order = Integer.compare(first.getOrder(), second.getOrder());
			if (order == 0 && first instanceof BiOrderable && second instanceof BiOrderable) {
				order = Integer.compare(((BiOrderable) first).getSecondaryOrder(), ((BiOrderable) second).getSecondaryOrder());
			}
			return order;
		};
	}

	public static interface BiOrderable extends Orderable {
		public int getSecondaryOrder();
	}
}