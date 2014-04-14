package org.vault.base.utilities.tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

import com.google.common.base.Objects;

public final class Tuple {

	public static final <V1> Single<V1> single(V1 v1) {
		return of(v1);
	}

	public static final <V1, V2> Pair<V1, V2> pair(V1 v1, V2 v2) {
		return of(v1, v2);
	}

	public static final <V1, V2, V3> Triple<V1, V2, V3> triple(V1 v1, V2 v2, V3 v3) {
		return of(v1, v2, v3);
	}

	public static final <V1> Tuple1<V1> of(V1 v1) {
		return new Tuple1<V1>(v1);
	}

	public static final <V1, V2> Tuple2<V1, V2> of(V1 v1, V2 v2) {
		return new Tuple2<V1, V2>(v1, v2);
	}

	public static final <V1, V2, V3> Tuple3<V1, V2, V3> of(V1 v1, V2 v2, V3 v3) {
		return new Tuple3<V1, V2, V3>(v1, v2, v3);
	}

// Alias For Tuple1
	public static interface Single<V1> extends Iterable<Object> {
		V1 getFirst();
	}

	public static class Tuple1<V1> implements Single<V1>, Serializable {
		private static final long serialVersionUID = 4006475675164664408L;
		public final V1 _1;

		private Tuple1(V1 v1) {
			_1 = v1;
		}

		@Override
		public V1 getFirst() {
			return _1;
		}

		@Override
		public String toString() {
			return String.format("Tuple1(%s)", _1);
		}

		@Override
		public Iterator<Object> iterator() {
			return Arrays.<Object> asList(_1).iterator();
		};

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 0;
			result = prime * result + ((getFirst() == null) ? 0 : getFirst().hashCode());
			return result;
		}
	}

// Alias for Tuple2
	public static interface Pair<V1, V2> extends Single<V1>, Serializable {
		V2 getSecond();
	}

	public static class Tuple2<V1, V2> extends Tuple1<V1> implements Pair<V1, V2> {
		private static final long serialVersionUID = 7567525698416053358L;
		public final V2 _2;

		private Tuple2(V1 v1, V2 v2) {
			super(v1);
			_2 = v2;
		}

		@Override
		public V2 getSecond() {
			return _2;
		}

		@Override
		public String toString() {
			return String.format("Tuple2(%s,%s)", _1, _2);
		}

		@Override
		public Iterator<Object> iterator() {
			return Arrays.<Object> asList(_1, _2).iterator();
		};

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			Tuple2<?, ?> other = (Tuple2<?, ?>) obj;

			if (!Objects.equal(this.getFirst(), other.getFirst())) {
				return false;
			}

			if (!Objects.equal(this.getSecond(), other.getSecond())) {
				return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + ((getSecond() == null) ? 0 : getSecond().hashCode());
			return result;
		}
	}

// Alias for Tuple3
	public static interface Triple<V1, V2, V3> extends Pair<V1, V2>, Serializable {
		V3 getThird();
	}

	public static class Tuple3<V1, V2, V3> extends Tuple2<V1, V2> implements Triple<V1, V2, V3> {
		private static final long serialVersionUID = -6298410011629728265L;
		public final V3 _3;

		private Tuple3(V1 v1, V2 v2, V3 v3) {
			super(v1, v2);
			_3 = v3;
		}

		@Override
		public V3 getThird() {
			return _3;
		}

		@Override
		public String toString() {
			return String.format("Tuple3(%s,%s,%s)", _1, _2, _3);
		};

		@Override
		public Iterator<Object> iterator() {
			return Arrays.<Object> asList(_1, _2, _3).iterator();
		};
	}

}