package org.vault.core.managed.bootstrap.handlers;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.vault.base.collections.VCollections;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.base.utilities.configuration.Configurations;

@Component
public class LoggingBootstrapHandler extends CoreBootstrapHandler implements ApplicationContextAware {
	private static String LOGGING_CONFIG_LOCATION = "bootstrap/log4j-bootstrap.xml";
	private ApplicationContext applicationContext;

	public void run() {
		LogManager.resetConfiguration();

		DOMConfigurator configurator = new DOMConfigurator();
		ResourceInputStream resource =
				VCollections.getSingleResult(Configurations.getConfigurations(Configurations.createClasspathLocation(LOGGING_CONFIG_LOCATION), applicationContext));

		configurator.doConfigure(resource, LogManager.getLoggerRepository());
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
