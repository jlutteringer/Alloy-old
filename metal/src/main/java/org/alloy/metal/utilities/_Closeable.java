package org.alloy.metal.utilities;

import java.io.Closeable;

import org.alloy.metal.function.ExceptionConsumer;
import org.alloy.metal.function.ExceptionFunction;

public class _Closeable {
	public static <T extends Closeable> void with(T closeable, ExceptionConsumer<T> consumer) {
		_Exception.propagate(() -> {
			try {
				consumer.accept(closeable);
			} finally {
				closeable.close();
			}
		});
	}

	public static <T extends Closeable, N> N withResult(T closeable, ExceptionFunction<T, N> operation) {
		return _Exception.propagate(() -> {
			try {
				return operation.apply(closeable);
			} finally {
				closeable.close();
			}
		});
	}
}