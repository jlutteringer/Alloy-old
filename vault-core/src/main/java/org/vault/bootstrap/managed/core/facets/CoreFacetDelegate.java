package org.vault.bootstrap.managed.core.facets;

import org.springframework.stereotype.Component;
import org.vault.core.facets.service.FacetDelegate;
import org.vault.module.registry.core.CoreFacet;

@Component
public class CoreFacetDelegate extends FacetDelegate {
	@Override
	protected Class<?> getFacetType() {
		return CoreFacet.class;
	}
}
