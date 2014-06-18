package org.vault.core.facets.service;

import org.vault.base.delegator.AbstractDelegate;
import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;
import org.vault.base.reflection.VReflection;
import org.vault.base.utilities.matcher.Matcher;

public abstract class AbstractFacetDelegate<T extends N, N extends Facet> extends AbstractDelegate<Class<? extends Facet>> implements FacetDelegate<T> {
	@Override
	protected Matcher<Class<? extends Facet>> getInternalMatcher() {
		return (input) -> input.isAssignableFrom(this.getFacetType());
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getFacetType() {
		return (Class<T>) VReflection.getTypeArguments(AbstractFacetDelegate.class, this.getClass()).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A extends Facet> A getFacet(Class<A> clazz, FacetedObject<? extends Facet> object) {
		return (A) this.getFacet((FacetedObject<N>) object);
	}

	protected abstract T getFacet(FacetedObject<N> object);
}