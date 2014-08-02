package org.alloy.metal.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.core.io.Resource;

import com.google.common.base.Throwables;

public class _Stream {
	public static String toString(InputStream from) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {

			br = new BufferedReader(new InputStreamReader(from));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

		} catch (IOException e) {
			throw Throwables.propagate(e);
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}

	public static Function<Resource, InputStream> transformer() {
		return (resource) -> {
			try {
				return resource.getInputStream();
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}
		};
	}

	public static <T> void withStream(InputStream stream, Consumer<InputStream> consumer) {
		withStream(Function.identity(), stream, consumer);
	}

	public static <T> void withStream(Function<T, InputStream> transformer, T item, Consumer<InputStream> consumer) {
		InputStream stream = null;
		try {
			stream = transformer.apply(item);
			consumer.accept(stream);
		} finally {
			if (stream != null) {
				try {
					stream.close();
				} catch (IOException e) {
					throw Throwables.propagate(e);
				}
			}
		}
	}
}
