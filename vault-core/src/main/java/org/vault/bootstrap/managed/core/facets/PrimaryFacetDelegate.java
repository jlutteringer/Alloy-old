package org.vault.bootstrap.managed.core.facets;

import org.springframework.stereotype.Component;
import org.vault.base.facets.FacetedObject;
import org.vault.base.module.domain.ModuleFacet;
import org.vault.base.module.domain.PrimaryModuleFacet;
import org.vault.core.facets.service.ModuleFacetDelegate;
import org.vault.module.registry.core.CoreFacet;

@Component
public class PrimaryFacetDelegate extends ModuleFacetDelegate<PrimaryModuleFacet> {
	@Override
	protected PrimaryModuleFacet getFacet(FacetedObject<ModuleFacet> object) {
		return new CoreFacet();
	}
}
