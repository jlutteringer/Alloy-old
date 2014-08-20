package org.alloy.site.resource.bundle.configuration;

public class ResourceConfigurations {
	public static ResourceConfigurationBuilder create(String name) {
		return new ResourceConfigurationBuilder(name);
	}

	public static class ResourceConfigurationBuilder {
		private ResourceConfiguration config = new ResourceConfiguration();
		private String dir = "";

		public ResourceConfigurationBuilder(String name) {
			config.setName(name);
		}

		public ResourceConfigurationBuilder dir(String dir) {
			this.dir = dir;
			return this;
		}

		public ResourceConfigurationBuilder addPath(String path) {
			AntPathConfigurationComponent component = new AntPathConfigurationComponent();
			component.setPattern(dir + path);
			config.getComponents().add(component);
			return this;
		}

		public ResourceConfigurationBuilder addResource(String name) {
			NameConfigurationComponent component = new NameConfigurationComponent();
			component.setName(dir + name);
			config.getComponents().add(component);
			return this;
		}

		public ResourceConfiguration build() {
			return config;
		}
	}
}