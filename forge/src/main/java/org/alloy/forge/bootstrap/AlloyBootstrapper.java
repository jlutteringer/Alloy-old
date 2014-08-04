package org.alloy.forge.bootstrap;

import java.util.List;
import java.util.function.Function;

import org.alloy.forge.context.AlloyApplicationContext;
import org.alloy.forge.enumeration.EnumerationConfigurer;
import org.alloy.forge.managed.configuration.ContextConfigurationManager;
import org.alloy.forge.managed.context.BootstrappedContextManager;
import org.alloy.forge.managed.initialization.PreInitializationContext;
import org.alloy.forge.managed.merge.ContextMergeManager;
import org.alloy.metal.configuration.AlloyConfigurationConstants;
import org.alloy.metal.configuration.ConfigurationLocation;
import org.alloy.metal.logging._Logging;
import org.alloy.metal.resource.ResourceInputStream;
import org.alloy.metal.resource._Resource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

public class AlloyBootstrapper {
	private static Logger logger = LogManager.getLogger(AlloyBootstrapper.class);
	private List<String> bootstrapConfigurationLocations;

	private ClassPathXmlApplicationContext bootstrapApplicationContext = null;

	private Function<Resource, AlloyApplicationContext> applicationContextCreator;

	public AlloyBootstrapper(Function<Resource, AlloyApplicationContext> applicationContextCreator) {
		this.applicationContextCreator = applicationContextCreator;
	}

	public AlloyApplicationContext bootstrap() {
		if (bootstrapApplicationContext == null) {
			this.start();
		}

		logger.debug("Beginning Alloy bootstrap...");

		List<ConfigurationLocation> configurationLocations = this.bootstrapConfigurationLocations();
		AlloyApplicationContext applicationContext = this.createMergeContext(this.mergeConfigurations(configurationLocations));

		return applicationContext;
	}

	protected AlloyApplicationContext createMergeContext(Resource resource) {
		AlloyApplicationContext mergeContext = applicationContextCreator.apply(resource);

		BootstrappedContextManager bootstrappedContextManager = bootstrapApplicationContext.getBean(BootstrappedContextManager.class);
		mergeContext.setBootstrappedBeanFactory(bootstrappedContextManager.createBootstrappedBeanFactory());

		return mergeContext;
	}

	public void start() {
		bootstrapApplicationContext = new ClassPathXmlApplicationContext();

		EnumerationConfigurer.configureEnumerations();
		_Logging.configureLog4j(
				ResourceInputStream.transformer().apply(_Resource.getResource(AlloyConfigurationConstants.FORGE_LOG4J_RESOURCE, bootstrapApplicationContext)));

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
