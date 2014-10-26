package org.alloy.forge.managed.facets;

import org.alloy.forge.module.CoreFacet;
import org.alloy.forge.module.ModuleFacet;
import org.alloy.forge.module.ModuleFacetDelegate;
import org.alloy.forge.module.PrimaryModuleFacet;
import org.alloy.metal.facets.FacetedObject;
import org.springframework.stereotype.Component;

@Component
public class PrimaryFacetDelegate extends ModuleFacetDelegate<PrimaryModuleFacet> {
	@Override
	protected PrimaryModuleFacet getFacetInternal(FacetedObject<ModuleFacet> object) {
		return new CoreFacet();
	}
}
