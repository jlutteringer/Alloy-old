package org.alloy.metal.function;

import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class Option<T> extends AbstractList<T> {
	private static final Option<?> EMPTY = new Option<>();

	private final T value;

	private Option() {
		this.value = null;
	}

	public static <T> Option<T> empty() {
		@SuppressWarnings("unchecked")
		Option<T> t = (Option<T>) EMPTY;
		return t;
	}

	private Option(T value) {
		this.value = Objects.requireNonNull(value);
	}

	public static <T> Option<T> of(T value) {
		return new Option<>(value);
	}

	public static <T> Option<T> of(Optional<T> value) {
		return value.isPresent() ? of(value.get()) : empty();
	}

	public static <T> Option<T> ofNullable(T value) {
		return value == null ? empty() : of(value);
	}

	public static <T> Function<Optional<T>, Option<T>> transformer() {
		return (option) -> Option.of(option);
	}

	public T get() {
		if (value == null) {
			throw new NoSuchElementException("No value present");
		}
		return value;
	}

	public boolean isPresent() {
		return value != null;
	}

	public void ifPresent(Consumer<? super T> consumer) {
		if (value != null)
			consumer.accept(value);
	}

	public Option<T> filter(Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		if (!isPresent())
			return this;
		else
			return predicate.test(value) ? this : empty();
	}

	public <U> Option<U> map(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		if (!isPresent())
			return empty();
		else {
			return Option.ofNullable(mapper.apply(value));
		}
	}

	public <U> Option<U> flatMap(Function<? super T, Option<U>> mapper) {
		Objects.requireNonNull(mapper);
		if (!isPresent())
			return empty();
		else {
			return Objects.requireNonNull(mapper.apply(value));
		}
	}

	public T orElse(T other) {
		return value != null ? value : other;
	}

	public T orElseGet(Supplier<? extends T> other) {
		return value != null ? value : other.get();
	}

	public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
		if (value != null) {
			return value;
		} else {
			throw exceptionSupplier.get();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Optional)) {
			return false;
		}

		Option<?> other = (Option<?>) obj;
		return Objects.equals(value, other.value);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(value);
	}

	@Override
	public String toString() {
		return value != null
				? String.format("VOptional[%s]", value)
				: "VOptional.empty";
	}

	@Override
	public T get(int index) {
		if (index == 0) {
			return this.orElseThrow(IndexOutOfBoundsException::new);
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public int size() {
		if (this.isPresent()) {
			return 1;
		}
		return 0;
	}
}