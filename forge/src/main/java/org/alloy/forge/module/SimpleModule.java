package org.alloy.forge.module;

import org.alloy.metal.facets.ProvidedFacetedObject;

public abstract class SimpleModule extends ProvidedFacetedObject<ModuleFacet> implements Module {
	protected String name;
	protected String friendlyName;
	protected ModuleType type = ModuleType.MODULE;

	protected DependencyContext dependencies = new DependencyContext();

	@Override
	public String getName() {
		return name;
	}

	@Override
	public ModuleType getType() {
		return type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(ModuleType type) {
		this.type = type;
	}

	@Override
	public DependencyContext getDependencies() {
		return dependencies;
	}

	@Override
	public String toString() {
		return name + " " + type;
	}

	@Override
	public String getFriendlyName() {
		return friendlyName;
	}
}
