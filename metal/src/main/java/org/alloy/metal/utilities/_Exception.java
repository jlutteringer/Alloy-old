package org.alloy.metal.utilities;

import java.util.Optional;

import org.alloy.metal.function.ExceptionOperation;
import org.alloy.metal.function.ExceptionSupplierOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Throwables;

public class _Exception {
	private static final Logger logger = LogManager.getLogger(_Exception.class);

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

	public static void ignore(ExceptionOperation operation) {
		try {
			operation.apply();
		} catch (Exception e) {
			logger.debug("Ignoring exception: ", e);
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
