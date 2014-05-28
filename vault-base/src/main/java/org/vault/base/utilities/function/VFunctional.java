package org.vault.base.utilities.function;

public interface VFunctional {
	public <T> T invoke(VMethod<T> invocation);
}
