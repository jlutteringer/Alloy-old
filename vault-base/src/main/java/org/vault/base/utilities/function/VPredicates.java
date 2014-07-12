package org.vault.base.utilities.function;

import java.util.function.Predicate;

public class VPredicates {
	@SafeVarargs
	public static <T> Predicate<T> and(Predicate<T>... predicates) {
		return (object) -> {
			for (Predicate<T> predicate : predicates) {
				if (!predicate.test(object)) {
					return false;
				}
			}
			return true;
		};
	}

	public static <T> Predicate<T> isDefined() {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> PredicateBuilder<T> build(Class<T> clazz) {
		return new PredicateBuilder<T>();
	}

	public static class PredicateBuilder<T> {

		public PredicateBuilder<T> and(Predicate<T> predicate) {
			// TODO Auto-generated method stub
			return null;
		}

		public PredicateBuilder<T> or(Predicate<T> predicate) {
			// TODO Auto-generated method stub
			return null;
		}

		public PredicateBuilder<T> continueIf(Predicate<T> predicate) {
			// TODO Auto-generated method stub
			return null;
		}

		public PredicateBuilder<T> stopIf(Predicate<T> predicate) {
			// TODO Auto-generated method stub
			return null;
		}

		public boolean test(T objectToTest) {
			// TODO Auto-generated method stub
			return false;
		}

		public Predicate<T> finish() {
			return null;
		}
	}
}
