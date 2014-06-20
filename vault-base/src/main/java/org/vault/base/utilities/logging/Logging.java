package org.vault.base.utilities.logging;

import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory.ConfigurationSource;
import org.apache.logging.log4j.core.config.XMLConfigurationFactory;

public class Logging {
	public static void configureLog4j(InputStream resource) {
		ConfigurationSource source = new ConfigurationSource(resource);
		Configuration config = XMLConfigurationFactory.getInstance().getConfiguration(source);
		LoggerContext ctx = (LoggerContext) LogManager.getContext();
		ctx.stop();
		ctx.start(config);
	}
}