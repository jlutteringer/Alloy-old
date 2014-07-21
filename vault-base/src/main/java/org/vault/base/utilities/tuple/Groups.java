package org.vault.base.utilities.tuple;

import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Iterator;

import org.vault.base.utilities.tuple.Tuple.Pair;
import org.vault.base.utilities.tuple.Tuple.Single;

public class Groups {
	public static <T> BiGroup<T> group(T first, T second) {
		return new BiGroup<>(first, second);
	}

	public static class Group<T> extends AbstractSet<T> implements Single<T> {
		private static final long serialVersionUID = 3392675181513970277L;
		private final T first;

		protected Group(T first) {
			this.first = first;
		}

		@Override
		public T getFirst() {
			return first;
		}

		@Override
		public Iterator<T> iterator() {
			return Arrays.asList(first).iterator();
		}

		@Override
		public int size() {
			return 1;
		}
	}

	public static class BiGroup<T> extends Group<T> implements Pair<T, T> {
		private static final long serialVersionUID = -659025538795845933L;
		private final T second;

		protected BiGroup(T first, T second) {
			super(first);
			this.second = second;
		}

		@Override
		public T getSecond() {
			return second;
		}

		@Override
		public Iterator<T> iterator() {
			return Arrays.asList(this.getFirst(), second).iterator();
		}

		@Override
		public int size() {
			return 2;
		}
	}
}
