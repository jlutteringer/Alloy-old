package org.alloy.metal.logging;

import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory.ConfigurationSource;
import org.apache.logging.log4j.core.config.XMLConfigurationFactory;

public class _Logging {
	public static void configureLog4j(InputStream resource) {
		ConfigurationSource source = new ConfigurationSource(resource);
		Configuration config = XMLConfigurationFactory.getInstance().getConfiguration(source);
		LoggerContext ctx = (LoggerContext) LogManager.getContext();
		ctx.stop();
		ctx.start(config);
	}

	public static OutputStream toOutputStream(Logger logger) {
		return new LogOutputStream(logger, Level.DEBUG);
	}
}