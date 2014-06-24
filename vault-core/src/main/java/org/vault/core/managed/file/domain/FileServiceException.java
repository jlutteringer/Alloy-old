package org.vault.core.managed.file.domain;

public class FileServiceException extends RuntimeException {
	private static final long serialVersionUID = -2907169824426620254L;

	public FileServiceException() {
		super();
	}

	public FileServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileServiceException(String message) {
		super(message);
	}

	public FileServiceException(Throwable cause) {
		super(cause);
	}
}
