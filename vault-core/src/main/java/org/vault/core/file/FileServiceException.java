package org.vault.core.file;

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
