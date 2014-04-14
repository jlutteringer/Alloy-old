package org.vault.base.utilities.matcher;

import java.util.List;

import org.vault.base.utilities.domain.Identifiable;

import com.google.common.collect.Lists;

public class Matchers {
	@SafeVarargs
	public static <T> Matcher<T> andMatchers(Matcher<T>... matchers) {
		return andMatchers(Lists.newArrayList(matchers));
	}

	public static <T> Matcher<T> andMatchers(List<Matcher<T>> matchers) {
		return new AndCompositeMatcher<T>().addInternalMatchers(matchers);
	}

	@SafeVarargs
	public static <T> Matcher<T> orMatchers(Matcher<T>... matchers) {
		return orMatchers(Lists.newArrayList(matchers));
	}

	public static <T> Matcher<T> orMatchers(List<Matcher<T>> matchers) {
		return new OrCompositeMatcher<T>().addInternalMatchers(matchers);
	}

	public static <T extends Identifiable> Matcher<T> matchId(final Long id) {
		return new AbstractMatcher<T>() {
			@Override
			public boolean matches(T input) {
				if (input.getId().equals(id)) {
					return true;
				}
				return false;
			}
		};
	}

	public static <T> Matcher<T> matchAll() {
		return new AbstractMatcher<T>() {
			@Override
			public boolean matches(T input) {
				return true;
			}
		};
	}

	public static <T> Matcher<T> matchClass(Class<?> clazz) {
		return new ClassMatcher<T>(clazz);
	}

	static class ClassMatcher<T> extends AbstractMatcher<T> {
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
