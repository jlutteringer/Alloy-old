package org.vault.core.bootstrap;

import java.util.Collections;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.core.module.domain.ModuleHierarchy;
import org.vault.core.module.service.ModuleLoader;
import org.vault.extensibility.bootstrap.domain.Bootstrap;
import org.vault.extensibility.context.MergeClasspathXmlApplicationContext;

public class CoreApplicationBootstrapper implements Bootstrap {
	private List<ConfigurationLocation> bootstrapConfigurationLocations;

	public CoreApplicationBootstrapper(ConfigurationLocation bootstrapConfigurationLocation) {
		this.bootstrapConfigurationLocations = Collections.singletonList(bootstrapConfigurationLocation);
	}

	public CoreApplicationBootstrapper(List<ConfigurationLocation> bootstrapConfigurationLocations) {
		this.bootstrapConfigurationLocations = bootstrapConfigurationLocations;
	}

	public ApplicationContext bootstrap() {
		List<ConfigurationLocation> configurationLocations = this.bootstrapConfigurationLocations();

		List<String> parsedConfigurationLocations = Configurations.parseLocations(configurationLocations);

		MergeClasspathXmlApplicationContext mergedContext = new MergeClasspathXmlApplicationContext();
		mergedContext.setPatchLocations(parsedConfigurationLocations);

		mergedContext.refresh();
		mergedContext.start();
		mergedContext.registerShutdownHook();
		return mergedContext;
	}

	private List<ConfigurationLocation> bootstrapConfigurationLocations() {
		ClassPathXmlApplicationContext bootstrapApplicationContext =
				new ClassPathXmlApplicationContext(Configurations.parseLocations(this.getBootstrapConfigurationLocations()).toArray(new String[0]));

		bootstrapApplicationContext.refresh();
		bootstrapApplicationContext.start();

		ModuleLoader loader = bootstrapApplicationContext.getBean(ModuleLoader.class);

		ModuleHierarchy moduleHierarchy = loader.getModuleHierarchy();
		List<ConfigurationLocation> configurationLocations = loader.buildConfigurationLocations(moduleHierarchy);

		bootstrapApplicationContext.close();

		return configurationLocations;
	}

	public List<ConfigurationLocation> getBootstrapConfigurationLocations() {
		return bootstrapConfigurationLocations;
	}

	public void setBootstrapConfigurationLocation(List<ConfigurationLocation> bootstrapConfigurationLocations) {
		this.bootstrapConfigurationLocations = bootstrapConfigurationLocations;
	}
}
