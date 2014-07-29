package org.vault.base.utilities.function;

@FunctionalInterface
public interface ExceptionFunction<T, R> {
	public R apply(T t) throws Exception;
}