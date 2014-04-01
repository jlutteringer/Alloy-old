package org.vault.core.bootstrap;

import java.util.Collections;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.core.module.domain.ModuleHierarchy;
import org.vault.core.module.service.ModuleLoader;
import org.vault.extensibility.bootstrap.domain.Bootstrap;

public class CoreApplicationBootstrapper implements Bootstrap {
	private List<ConfigurationLocation> bootstrapConfigurationLocations;

	public CoreApplicationBootstrapper(ConfigurationLocation bootstrapConfigurationLocation) {
		this.bootstrapConfigurationLocations = Collections.singletonList(bootstrapConfigurationLocation);
	}

	public CoreApplicationBootstrapper(List<ConfigurationLocation> bootstrapConfigurationLocations) {
		this.bootstrapConfigurationLocations = bootstrapConfigurationLocations;
	}

	public void bootstrap() {
		ClassPathXmlApplicationContext bootstrapApplicationContext =
				new ClassPathXmlApplicationContext(Configurations.parseLocations(this.getBootstrapConfigurationLocations()));

		bootstrapApplicationContext.refresh();
		bootstrapApplicationContext.start();

		ModuleLoader loader = bootstrapApplicationContext.getBean(ModuleLoader.class);
		ModuleHierarchy moduleHierarchy = loader.getModuleHierarchy();

		System.out.println(moduleHierarchy.getModules().toString());

		bootstrapApplicationContext.close();
	}

	public List<ConfigurationLocation> getBootstrapConfigurationLocations() {
		return bootstrapConfigurationLocations;
	}

	public void setBootstrapConfigurationLocation(List<ConfigurationLocation> bootstrapConfigurationLocations) {
		this.bootstrapConfigurationLocations = bootstrapConfigurationLocations;
	}
}
