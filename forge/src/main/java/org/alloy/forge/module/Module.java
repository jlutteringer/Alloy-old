package org.alloy.forge.module;

import org.alloy.metal.facets.FacetedObject;

public interface Module extends FacetedObject<ModuleFacet> {
	public String getName();

	public String getFriendlyName();

	public ModuleType getType();

	public DependencyContext getDependencies();
}