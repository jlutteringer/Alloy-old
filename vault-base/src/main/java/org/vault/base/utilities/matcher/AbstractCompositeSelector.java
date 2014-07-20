package org.vault.base.utilities.matcher;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractCompositeSelector<T> extends AbstractSelector<T> {
	protected List<Selector<T>> internalSelectors = Lists.newArrayList();

	public AbstractCompositeSelector<T> addInternalSelector(Selector<T> matcher) {
		internalSelectors.add(matcher);
		return this;
	}

	public AbstractCompositeSelector<T> addInternalSelectors(List<Selector<T>> matchers) {
		internalSelectors.addAll(matchers);
		return this;
	}
}