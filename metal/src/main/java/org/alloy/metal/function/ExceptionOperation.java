package org.alloy.metal.function;

@FunctionalInterface
public interface ExceptionOperation {
	public void apply() throws Exception;
}