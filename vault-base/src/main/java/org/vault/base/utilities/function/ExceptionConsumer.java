package org.vault.base.utilities.function;

@FunctionalInterface
public interface ExceptionConsumer<T> {
	public void accept(T t) throws Exception;
}