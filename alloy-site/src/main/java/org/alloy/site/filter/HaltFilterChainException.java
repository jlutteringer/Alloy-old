package org.alloy.site.filter;

public class HaltFilterChainException extends RuntimeException {
	private static final long serialVersionUID = -4138837912315748276L;

	// for serialization purposes
	protected HaltFilterChainException() {
		super();
	}

	public HaltFilterChainException(String message, Throwable cause) {
		super(message, cause);
	}

	public HaltFilterChainException(String message) {
		super(message);
	}

	public HaltFilterChainException(Throwable cause) {
		super(cause);
	}
}
