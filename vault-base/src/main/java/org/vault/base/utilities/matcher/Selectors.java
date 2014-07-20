package org.vault.base.utilities.matcher;

import java.util.List;
import java.util.function.Predicate;

import org.vault.base.domain.Identifiable;
import org.vault.base.utilities.function.VPredicates;

import com.google.common.collect.Lists;

public class Selectors {
	public static <T> Selector<Predicate<T>> reverseSelector(T element) {
		return getSelector(VPredicates.reverse(element));
	}

	@SafeVarargs
	public static <T> Selector<T> andSelectors(Selector<T>... selectors) {
		return andSelectors(Lists.newArrayList(selectors));
	}

	public static <T> Selector<T> andSelectors(List<Selector<T>> selectors) {
		return new AndCompositeSelector<T>().addInternalSelectors(selectors);
	}

	@SafeVarargs
	public static <T> Selector<T> orSelectors(Selector<T>... selectors) {
		return orSelectors(Lists.newArrayList(selectors));
	}

	public static <T> Selector<T> orSelectors(List<Selector<T>> selectors) {
		return new OrCompositeSelector<T>().addInternalSelectors(selectors);
	}

	public static <T extends Identifiable> Selector<T> matchId(Long id) {
		return getSelector(VPredicates.matchId(id));
	}

	public static <T> Selector<T> matchAll() {
		return getSelector(VPredicates.matchAll());
	}

	public static <T> Selector<T> matchClass(Class<?> clazz) {
		return new ClassSelector<T>(clazz);
	}

	static class ClassSelector<T> extends AbstractSelector<T> {
		private Class<?> clazz;

		public ClassSelector(Class<?> clazz) {
			this.clazz = clazz;
		}

		@Override
		public boolean test(T input) {
			if (input.getClass().equals(clazz)) {
				return true;
			}
			return false;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object o) {
			if (o instanceof ClassSelector) {
				return this.clazz.equals(((ClassSelector) o).clazz);
			}
			return false;
		}
	}

	public static <T> Selector<T> getSelector(Predicate<T> matcher) {
		return new ComposingSelector<T>(matcher);
	}
}
