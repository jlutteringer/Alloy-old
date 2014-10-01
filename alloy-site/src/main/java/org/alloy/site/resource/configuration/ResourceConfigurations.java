package org.alloy.site.resource.configuration;

import org.alloy.metal.collections.lists._List;

public class ResourceConfigurations {
	public static ResourceAliasConfigurationBuilder alias(String aliasPath) {
		return new ResourceAliasConfigurationBuilder(aliasPath);
	}

	public static ResourceConfiguration injectProperties(String... resourceLocations) {
		TransformResourceConfiguration configuration = new TransformResourceConfiguration();
		configuration.getResourceLocations().addAll(_List.list(resourceLocations));
		configuration.setInjectProperties(true);
		return configuration;
	}

	public static class ResourceAliasConfigurationBuilder {
		private ResourceAliasConfiguration configuration = new ResourceAliasConfiguration();

		public ResourceAliasConfigurationBuilder(String aliasPath) {
			configuration.setAliasPath(aliasPath);
		}

		public ResourceAliasConfigurationBuilder include(String resourceLocation) {
			configuration.getResourceLocations().add(resourceLocation);
			return this;
		}

		public ResourceConfiguration build() {
			return configuration;
		}
	}
}