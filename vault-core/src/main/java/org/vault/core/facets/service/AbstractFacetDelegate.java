package org.vault.core.facets.service;

import java.util.function.Predicate;

import org.alloy.metal.reflection._Reflection;
import org.alloy.metal.spring.delegator.AbstractDelegate;
import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;

public abstract class AbstractFacetDelegate<T extends N, N extends Facet> extends AbstractDelegate<Class<? extends Facet>> implements FacetDelegate<T> {
	@Override
	protected Predicate<Class<? extends Facet>> getInternalMatcher() {
		return (input) -> input.isAssignableFrom(this.getFacetType());
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getFacetType() {
		return (Class<T>) _Reflection.getTypeArguments(AbstractFacetDelegate.class, this.getClass()).get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A extends Facet> A getFacet(Class<A> clazz, FacetedObject<? extends Facet> object) {
		return (A) this.getFacet((FacetedObject<N>) object);
	}

	protected abstract T getFacet(FacetedObject<N> object);
}