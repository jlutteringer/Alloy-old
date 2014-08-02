package org.alloy.metal.function;

@FunctionalInterface
public interface ExceptionConsumer<T> {
	public void accept(T t) throws Exception;
}