package org.vault.bootstrap;

import java.util.List;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.vault.base.resource.ResourceInputStream;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.base.utilities.logging.Logging;
import org.vault.bootstrap.context.VaultApplicationContext;
import org.vault.bootstrap.managed.context.BootstrappedContextManager;
import org.vault.bootstrap.managed.context.configuration.ContextConfigurationManager;
import org.vault.bootstrap.managed.context.merge.ContextMergeManager;
import org.vault.bootstrap.managed.initialization.service.PreInitializationContext;
import org.vault.bootstrap.service.Bootstrap;

public class VaultApplicationBootstrapper implements Bootstrap {
	private static Logger logger = LogManager.getLogger(VaultApplicationBootstrapper.class);
	private List<String> bootstrapConfigurationLocations;

	private ClassPathXmlApplicationContext bootstrapApplicationContext = null;

	private Function<Resource, VaultApplicationContext> applicationContextCreator;

	public VaultApplicationBootstrapper(Function<Resource, VaultApplicationContext> applicationContextCreator) {
		this.applicationContextCreator = applicationContextCreator;
	}

	@Override
	public VaultApplicationContext bootstrap() {
		if (bootstrapApplicationContext == null) {
			this.start();
		}

		logger.debug("Beginning Vault bootstrap...");

		List<ConfigurationLocation> configurationLocations = this.bootstrapConfigurationLocations();
		VaultApplicationContext applicationContext = this.createMergeContext(this.mergeConfigurations(configurationLocations));

		return applicationContext;
	}

	protected VaultApplicationContext createMergeContext(Resource resource) {
		VaultApplicationContext mergeContext = applicationContextCreator.apply(resource);

		BootstrappedContextManager bootstrappedContextManager = bootstrapApplicationContext.getBean(BootstrappedContextManager.class);
		mergeContext.setInitializationContext(bootstrappedContextManager.createBootstrappedContext());

		return mergeContext;
	}

	public void start() {
		bootstrapApplicationContext = new ClassPathXmlApplicationContext();

		BootstrapUtils.configureEnumerations();
		Logging.configureLog4j(
				ResourceInputStream.transformer().apply(Configurations.resolveClasspathResource(VConfigurationFileConstants.BOOTSTRAP_LOG4J_RESOURCE, bootstrapApplicationContext)));

		bootstrapApplicationContext.setConfigLocations(getBootstrapConfigurationLocations().toArray(new String[0]));
		bootstrapApplicationContext.refresh();

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

	public void setBootstrapConfigurationLocations(List<String> bootstrapConfigurationLocations) {
		this.bootstrapConfigurationLocations = bootstrapConfigurationLocations;
	}
}
