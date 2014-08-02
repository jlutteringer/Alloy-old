package org.alloy.forge.managed.facets;

import org.alloy.metal.facets.Facet;
import org.alloy.metal.facets.FacetDelegate;
import org.alloy.metal.spring.delegator.AbstractDelegator;
import org.springframework.stereotype.Component;

@Component
public class FacetConfigurationDelegator extends AbstractDelegator<FacetDelegate<?>, Class<? extends Facet>> {

}
