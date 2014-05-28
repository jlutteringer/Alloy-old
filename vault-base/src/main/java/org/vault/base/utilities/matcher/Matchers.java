package org.vault.base.utilities.matcher;

import java.util.List;

import org.vault.base.domain.Identifiable;

import com.google.common.collect.Lists;

public class Matchers {
	public static <T> Selector<Matcher<T>> reverseMatcher(T element) {
		return new AbstractSelector<Matcher<T>>() {
			@Override
			public boolean matches(Matcher<T> input) {
				if (input.matches(element)) {
					return true;
				}
				return false;
			}
		};
	}

	@SafeVarargs
	public static <T> Selector<T> andMatchers(Selector<T>... matchers) {
		return andMatchers(Lists.newArrayList(matchers));
	}

	public static <T> Selector<T> andMatchers(List<Selector<T>> matchers) {
		return new AndCompositeMatcher<T>().addInternalMatchers(matchers);
	}

	@SafeVarargs
	public static <T> Selector<T> orMatchers(Selector<T>... matchers) {
		return orMatchers(Lists.newArrayList(matchers));
	}

	public static <T> Selector<T> orMatchers(List<Selector<T>> matchers) {
		return new OrCompositeMatcher<T>().addInternalMatchers(matchers);
	}

	public static <T extends Identifiable> Selector<T> matchId(final Long id) {
		return new AbstractSelector<T>() {
			@Override
			public boolean matches(T input) {
				if (input.getId().equals(id)) {
					return true;
				}
				return false;
			}
		};
	}

	public static <T> Selector<T> matchAll() {
		return new AbstractSelector<T>() {
			@Override
			public boolean matches(T input) {
				return true;
			}
		};
	}

	public static <T> Selector<T> matchClass(Class<?> clazz) {
		return new ClassMatcher<T>(clazz);
	}

	static class ClassMatcher<T> extends AbstractSelector<T> {
		private Class<?> clazz;

		public ClassMatcher(Class<?> clazz) {
			this.clazz = clazz;
		}

		@Override
		public boolean matches(T input) {
			if (input.getClass().equals(clazz)) {
				return true;
			}
			return false;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			if (o instanceof ClassMatcher) {
				return this.clazz.equals(((ClassMatcher) o).clazz);
			}
			return false;
		}
	}
}
