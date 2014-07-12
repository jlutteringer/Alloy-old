package org.vault.base.utilities.function;

@FunctionalInterface
public interface ExceptionSupplierOperation<T> {
	public T get() throws Exception;
}
