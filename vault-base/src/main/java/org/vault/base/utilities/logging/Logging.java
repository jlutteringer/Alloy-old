package org.vault.base.utilities.logging;

import java.io.InputStream;

import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;

import com.google.common.base.Throwables;

public class Logging {
	public static void configureLog4j(InputStream resource) {
		try {
			DOMConfigurator configurator = new DOMConfigurator();
			configurator.doConfigure(resource, LogManager.getLoggerRepository());
		} catch (FactoryConfigurationError e) {
			throw Throwables.propagate(e);
		}
	}
}
