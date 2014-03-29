package org.vault.base.utilities.function;

public interface Operation {
	public static interface Operation_0<T> extends Operation {
		public T apply();
	}

	public static interface Operation_1<T, N> extends Operation {
		public T apply(N first);
	}

	public static interface Operation_2<T, N, S> extends Operation {
		public T apply(N first, S second);
	}
}
