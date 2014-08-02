package org.vault.base.closeable;

import java.io.Closeable;

import org.vault.base.utilities.exception.Exceptions;
import org.vault.base.utilities.function.ExceptionConsumer;
import org.vault.base.utilities.function.ExceptionFunction;

public class _Closeable {
	public static <T extends Closeable> void with(T closeable, ExceptionConsumer<T> consumer) {
		Exceptions.propagate(() -> {
			try {
				consumer.accept(closeable);
			} finally {
				closeable.close();
			}
		});
	}

	public static <T extends Closeable, N> N withResult(T closeable, ExceptionFunction<T, N> operation) {
		return Exceptions.propagate(() -> {
			try {
				return operation.apply(closeable);
			} finally {
				closeable.close();
			}
		});
	}
}