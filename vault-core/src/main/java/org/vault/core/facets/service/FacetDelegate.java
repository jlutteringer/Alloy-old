package org.vault.core.facets.service;

import org.vault.base.delegator.AbstractDelegate;
import org.vault.base.utilities.matcher.Matcher;

public abstract class FacetDelegate extends AbstractDelegate<Class<?>> {
	@Override
	protected Matcher<Class<?>> getInternalMatcher() {
		return (input) -> input.isAssignableFrom(this.getFacetType());
	}

	protected abstract Class<?> getFacetType();
}
