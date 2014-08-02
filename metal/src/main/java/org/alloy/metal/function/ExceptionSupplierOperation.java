package org.alloy.metal.function;

@FunctionalInterface
public interface ExceptionSupplierOperation<T> {
	public T get() throws Exception;
}