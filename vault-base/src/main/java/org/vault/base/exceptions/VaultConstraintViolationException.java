package org.vault.base.exceptions;

public class VaultConstraintViolationException extends RuntimeException {
	private static final long serialVersionUID = -9222253172150351453L;

	public VaultConstraintViolationException(String msg) {
		super(msg);
	}

	public VaultConstraintViolationException(String msg, Throwable t) {
		super(msg, t);
	}
}
