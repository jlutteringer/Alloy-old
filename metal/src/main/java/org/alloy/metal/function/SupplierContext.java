package org.alloy.metal.function;

import java.util.function.Supplier;

public interface SupplierContext<T, N> {
	public StatefulSupplier<T, N> getPrimarySupplier();

	public Supplier<T> getStateSupplier();
}