package org.vault.bootstrap;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vault.base.module.domain.ModuleHierarchy;
import org.vault.base.module.service.ModuleLoader;
import org.vault.base.reflection.VReflection;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.base.utilities.logging.Logging;
import org.vault.bootstrap.managed.initialization.service.PreInitializationContext;
import org.vault.bootstrap.service.Bootstrap;
import org.vault.extensibility.context.MergeApplicationContext;

public abstract class AbstractCoreApplicationBootstrapper<T extends MergeApplicationContext> implements Bootstrap<T> {
	private static final Logger log = Logger.getLogger(AbstractCoreApplicationBootstrapper.class);
	private List<String> bootstrapConfigurationLocations;

	@Override
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

		Logging.configureLog4j(Configurations.resolveClasspathResource(VConfigurationFileConstants.BOOTSTRAP_LOG4J_RESOURCE, bootstrapApplicationContext));
		log.debug("Beginning Vault bootstrap...");

		bootstrapApplicationContext.refresh();
		bootstrapApplicationContext.start();

		ModuleLoader loader = bootstrapApplicationContext.getBean(ModuleLoader.class);

		ModuleHierarchy moduleHierarchy = loader.getModuleHierarchy();
		List<ConfigurationLocation> configurationLocations = loader.buildConfigurationLocations(moduleHierarchy);

		PreInitializationContext preInitialization = bootstrapApplicationContext.getBean(PreInitializationContext.class);
		preInitialization.initialize();

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
