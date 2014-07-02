package org.vault.base.utilities.function;

@FunctionalInterface
public interface ExceptionOperation {
	public void apply() throws Exception;
}
