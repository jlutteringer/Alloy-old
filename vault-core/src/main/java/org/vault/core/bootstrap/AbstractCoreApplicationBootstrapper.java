package org.vault.core.bootstrap;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vault.base.reflection.VReflection;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.core.module.domain.ModuleHierarchy;
import org.vault.core.module.service.ModuleLoader;
import org.vault.extensibility.bootstrap.domain.Bootstrap;
import org.vault.extensibility.bootstrap.domain.BootstrapHandlerContext;
import org.vault.extensibility.context.MergeApplicationContext;

public abstract class AbstractCoreApplicationBootstrapper<T extends MergeApplicationContext> implements Bootstrap<T> {
	private List<String> bootstrapConfigurationLocations;

	public T bootstrap() {
		List<ConfigurationLocation> configurationLocations = this.bootstrapConfigurationLocations();

		T mergedContext = this.createMergeContext();
		mergedContext.setPatchLocations(configurationLocations);
		return mergedContext;
	}

	@SuppressWarnings("unchecked")
	protected T createMergeContext() {
		Class<?> clazz = VReflection.getTypeArguments(AbstractCoreApplicationBootstrapper.class, this.getClass()).get(0);
		return (T) VReflection.construct(clazz);
	}

	private List<ConfigurationLocation> bootstrapConfigurationLocations() {
		ClassPathXmlApplicationContext bootstrapApplicationContext =
				new ClassPathXmlApplicationContext(getBootstrapConfigurationLocations().toArray(new String[0]));

		bootstrapApplicationContext.refresh();
		bootstrapApplicationContext.start();

		BootstrapHandlerContext handlers = bootstrapApplicationContext.getBean(BootstrapHandlerContext.class);
		handlers.run();

		ModuleLoader loader = bootstrapApplicationContext.getBean(ModuleLoader.class);

		ModuleHierarchy moduleHierarchy = loader.getModuleHierarchy();
		List<ConfigurationLocation> configurationLocations = loader.buildConfigurationLocations(moduleHierarchy);

		bootstrapApplicationContext.close();

		return configurationLocations;
	}

	public List<String> getBootstrapConfigurationLocations() {
		return bootstrapConfigurationLocations;
	}

	public void setBootstrapConfigurationLocation(List<String> bootstrapConfigurationLocations) {
		this.bootstrapConfigurationLocations = bootstrapConfigurationLocations;
	}
}
