package org.vault.base.utilities.matcher;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractCompositeMatcher<T> extends AbstractSelector<T> {
	protected List<Selector<T>> internalMatchers = Lists.newArrayList();

	public AbstractCompositeMatcher<T> addInternalMatcher(Selector<T> matcher) {
		internalMatchers.add(matcher);
		return this;
	}

	public AbstractCompositeMatcher<T> addInternalMatchers(List<Selector<T>> matchers) {
		internalMatchers.addAll(matchers);
		return this;
	}
}