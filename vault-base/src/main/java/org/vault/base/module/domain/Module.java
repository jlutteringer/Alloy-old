package org.vault.base.module.domain;

import java.util.List;

import org.vault.base.facets.FacetedObject;

public interface Module extends FacetedObject<ModuleFacet> {
	public String getName();

	public String getFriendlyName();

	public ModuleType getType();

	public List<String> getDependencies();
}