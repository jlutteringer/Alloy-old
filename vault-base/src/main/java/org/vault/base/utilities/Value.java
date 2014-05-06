package org.vault.base.utilities;

import java.io.Serializable;
import java.util.NoSuchElementException;

import org.vault.base.utilities.object.VObjects;

import com.google.common.base.Objects;

public abstract class Value<T> implements Serializable {
	private static final long serialVersionUID = 8836281423379233405L;

	public static <T> Value<T> of(T value) {
		return new Some<T>(value);
	}

	@SuppressWarnings("unchecked")
	public static <T> Value<T> none() {
		return (Value<T>) None.INSTANCE;
	}

	public abstract boolean isDefined();

	public abstract T get();

	public abstract T get(T defaultValue);

	public abstract T getOrThrow(Throwable e) throws Throwable;

	public abstract T getOrThrow(RuntimeException e);

	public abstract T getOrThrow(String message);

	@Override
	public abstract boolean equals(Object other);

	@Override
	public abstract int hashCode();

	@Override
	public abstract String toString();

	private static final class Some<T> extends Value<T> {

		private static final long serialVersionUID = 0;
		private final T value;

		public Some(T value) {
			this.value = value;
		}

		@Override
		public T get() {
			return value;
		}

		@Override
		public T get(T defaultValue) {
			return value;
		}

		@Override
		public T getOrThrow(Throwable e) throws Throwable {
			return value;
		}

		@Override
		public T getOrThrow(RuntimeException e) {
			return value;
		}

		@Override
		public T getOrThrow(String message) {
			return value;
		}

		@Override
		public boolean isDefined() {
			return true;
		}

		@Override
		public boolean equals(Object object) {
			if (object instanceof Some) {
				Some<?> other = (Some<?>) object;
				return Objects.equal(this.value, other.value);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return VObjects.hashCode(value);
		}

		@Override
		public String toString() {
			return String.format("Some(%s)", value);
		}
	}

	private static final class None extends Value<Object> {

		private static final long serialVersionUID = 0;
		private static final None INSTANCE = new None();

		@Override
		public Object get() {
			throw new NoSuchElementException();
		}

		@Override
		public boolean isDefined() {
			return false;
		}

		@Override
		public Object get(Object defaultValue) {
			return defaultValue;
		}

		@Override
		public Object getOrThrow(Throwable e) throws Throwable {
			throw e;
		}

		@Override
		public Object getOrThrow(RuntimeException e) {
			throw e;
		}

		@Override
		public Object getOrThrow(String message) {
			throw new NoSuchElementException(message);
		}

		@Override
		public boolean equals(Object object) {
			return object == this;
		}

		@Override
		public int hashCode() {
			return 0x598df91c;
		}

		@Override
		public String toString() {
			return "None";
		}

		private Object readResolve() {
			return INSTANCE;
		}
	}
}
