package org.vault.base.utilities.matcher;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractCompositeMatcher<T> extends AbstractMatcher<T> {
	protected List<Matcher<T>> internalMatchers = Lists.newArrayList();

	public AbstractCompositeMatcher<T> addInternalMatcher(Matcher<T> matcher) {
		internalMatchers.add(matcher);
		return this;
	}

	public AbstractCompositeMatcher<T> addInternalMatchers(List<Matcher<T>> matchers) {
		internalMatchers.addAll(matchers);
		return this;
	}
}