package org.vault.bootstrap.managed.core.facets;

import org.springframework.beans.factory.annotation.Autowired;
import org.vault.base.facets.Facet;
import org.vault.base.facets.FacetedObject;
import org.vault.core.facets.service.FacetConfigurationService;

public class CoreFacetConfigurationService implements FacetConfigurationService {
	@Autowired
	private FacetConfigurationDelegator delegator;

	@Override
	public <T extends Facet> T getFacet(Class<T> clazz, FacetedObject<?> object) {
		return delegator.delegateAndInvoke(clazz, FacetConfigurationDelegator.getFacet(clazz, object));
	}

	public String foo(Class<?> clazz, String input) {
		return delegator.delegateAndInvoke(clazz, FacetConfigurationDelegator.foo(input));
	}
}