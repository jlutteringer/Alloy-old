package org.vault.site.resource.bundle.configuration;

public class BundleConfigurations {

	public static BundleConfigurationBuilder create(String name) {
		return new BundleConfigurationBuilder(name);
	}

	public static class BundleConfigurationBuilder {
		private BundleConfiguration config = new BundleConfiguration();
		private String dir = "";

		public BundleConfigurationBuilder(String name) {
			config.setName(name);
		}

		public BundleConfigurationBuilder dir(String dir) {
			this.dir = dir;
			return this;
		}

		public BundleConfigurationBuilder addPath(String path) {
			AntPathConfigurationComponent component = new AntPathConfigurationComponent();
			component.setPattern(dir + path);
			config.getComponents().add(component);
			return this;
		}

		public BundleConfiguration build() {
			return config;
		}
	}
}