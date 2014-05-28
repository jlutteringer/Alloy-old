package org.vault.bootstrap;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.vault.base.reflection.VReflection;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.base.utilities.logging.Logging;
import org.vault.bootstrap.context.BootstrappedApplicationContext;
import org.vault.bootstrap.managed.context.BootstrappedContextManager;
import org.vault.bootstrap.managed.context.configuration.ContextConfigurationManager;
import org.vault.bootstrap.managed.context.merge.ContextMergeManager;
import org.vault.bootstrap.managed.initialization.service.PreInitializationContext;
import org.vault.bootstrap.service.Bootstrap;

public abstract class AbstractApplicationBootstrapper<T extends BootstrappedApplicationContext> implements Bootstrap<T> {
	private static final Logger log = Logger.getLogger(AbstractApplicationBootstrapper.class);
	private List<String> bootstrapConfigurationLocations;

	private ClassPathXmlApplicationContext bootstrapApplicationContext = null;

	@Override
	public T bootstrap() {
		if (bootstrapApplicationContext == null) {
			this.start();
		}

		log.debug("Beginning Vault bootstrap...");

		List<ConfigurationLocation> configurationLocations = this.bootstrapConfigurationLocations();
		T applicationContext = this.createMergeContext(this.mergeConfigurations(configurationLocations));

		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	protected T createMergeContext(Resource resource) {
		Class<?> clazz = VReflection.getTypeArguments(AbstractApplicationBootstrapper.class, this.getClass()).get(0);
		T mergeContext = (T) VReflection.construct(clazz, resource);

		BootstrappedContextManager bootstrappedContextManager = bootstrapApplicationContext.getBean(BootstrappedContextManager.class);
		mergeContext.setInitializationContext(bootstrappedContextManager.createBootstrappedContext());

		return mergeContext;
	}

	public void start() {
		bootstrapApplicationContext =
				new ClassPathXmlApplicationContext(getBootstrapConfigurationLocations().toArray(new String[0]));

		Logging.configureLog4j(Configurations.resolveClasspathResource(VConfigurationFileConstants.BOOTSTRAP_LOG4J_RESOURCE, bootstrapApplicationContext));

		PreInitializationContext preInitialization = bootstrapApplicationContext.getBean(PreInitializationContext.class);
		preInitialization.initialize();
	}

	public void stop() {
		bootstrapApplicationContext.close();
	}

	private List<ConfigurationLocation> bootstrapConfigurationLocations() {
		List<ConfigurationLocation> configurationLocations =
				bootstrapApplicationContext.getBean(ContextConfigurationManager.class).buildConfigurationLocations();

		return configurationLocations;
	}

	private Resource mergeConfigurations(List<ConfigurationLocation> configurationLocations) {
		return bootstrapApplicationContext.getBean(ContextMergeManager.class).getMergedResource(configurationLocations);
	}

	public List<String> getBootstrapConfigurationLocations() {
		return bootstrapConfigurationLocations;
	}

	public void setBootstrapConfigurationLocation(List<String> bootstrapConfigurationLocations) {
		this.bootstrapConfigurationLocations = bootstrapConfigurationLocations;
	}
}
