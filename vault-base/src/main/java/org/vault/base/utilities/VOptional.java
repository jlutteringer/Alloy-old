package org.vault.base.utilities;

import java.util.AbstractList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class VOptional<T> extends AbstractList<T> {
	private static final VOptional<?> EMPTY = new VOptional<>();

	private final T value;

	private VOptional() {
		this.value = null;
	}

	public static <T> VOptional<T> empty() {
		@SuppressWarnings("unchecked")
		VOptional<T> t = (VOptional<T>) EMPTY;
		return t;
	}

	private VOptional(T value) {
		this.value = Objects.requireNonNull(value);
	}

	public static <T> VOptional<T> of(T value) {
		return new VOptional<>(value);
	}

	public static <T> VOptional<T> of(Optional<T> value) {
		return value.isPresent() ? of(value.get()) : empty();
	}

	public static <T> VOptional<T> ofNullable(T value) {
		return value == null ? empty() : of(value);
	}

	public static <T> Function<Optional<T>, VOptional<T>> transformer() {
		return (option) -> VOptional.of(option);
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

	public VOptional<T> filter(Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		if (!isPresent())
			return this;
		else
			return predicate.test(value) ? this : empty();
	}

	public <U> VOptional<U> map(Function<? super T, ? extends U> mapper) {
		Objects.requireNonNull(mapper);
		if (!isPresent())
			return empty();
		else {
			return VOptional.ofNullable(mapper.apply(value));
		}
	}

	public <U> VOptional<U> flatMap(Function<? super T, VOptional<U>> mapper) {
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

		VOptional<?> other = (VOptional<?>) obj;
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