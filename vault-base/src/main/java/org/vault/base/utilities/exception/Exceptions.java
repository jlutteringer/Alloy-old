package org.vault.base.utilities.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vault.base.utilities.function.ExceptionOperation;

import com.google.common.base.Throwables;

public class Exceptions {
	private static final Logger logger = LogManager.getLogger(Exceptions.class);

	public static void propagate(ExceptionOperation operation) {
		try {
			operation.apply();
		} catch (Exception e) {
			Throwables.propagate(e);
		}
	}

	public static void ignore(ExceptionOperation operation) {
		try {
			operation.apply();
		} catch (Exception e) {
			logger.debug("Ignoring exception: ", e);
		}
	}
}
