package org.vault.base.utilities.exception;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vault.base.utilities.function.ExceptionOperation;
import org.vault.base.utilities.function.ExceptionSupplierOperation;

import com.google.common.base.Throwables;

public class Exceptions {
	private static final Logger logger = LogManager.getLogger(Exceptions.class);

	public static void propagate(ExceptionOperation operation) {
		try {
			operation.apply();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	public static <T> T propagate(ExceptionSupplierOperation<T> operation) {
		try {
			return operation.get();
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	public static <T> Optional<T> ignore(ExceptionSupplierOperation<T> operation) {
		try {
			return Optional.of(operation.get());
		} catch (Exception e) {
			logger.debug("Ignoring exception: ", e);
			return Optional.empty();
		}
	}
}
