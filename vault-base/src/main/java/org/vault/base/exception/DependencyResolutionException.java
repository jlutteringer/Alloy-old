package org.vault.base.exception;

public class DependencyResolutionException extends RuntimeException {
	private static final long serialVersionUID = -6522687327094667047L;

	public DependencyResolutionException(String message) {
		super(message);
	}
}
