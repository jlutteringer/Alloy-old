package org.vault.base.module.domain;

import org.vault.base.facets.FacetedObject;

public interface Module extends FacetedObject<ModuleFacet> {
	public String getName();

	public String getFriendlyName();

	public ModuleType getType();

	public DependencyContext getDependencies();
}