package org.vault.core.test;

import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.core.bootstrap.CoreApplicationBootstrapper;
import org.vault.extensibility.context.MergeApplicationContext;
import org.vault.extensibility.logging.MergeLog4jConfiguration;

import com.google.common.collect.Lists;

public class Tester {
	public static void main(String[] args) throws Exception {
		CoreApplicationBootstrapper bootstrap = new CoreApplicationBootstrapper();
		bootstrap.setBootstrapConfigurationLocation(Lists.newArrayList("bootstrap/vault-bootstrap-applicationContext.xml"));

		MergeApplicationContext context = bootstrap.bootstrap();

		ConfigurationLocation config1 = Configurations.createEnvironmentLocation("log4j-%s.xml", "DEV");

		MergeLog4jConfiguration log4jConfiguration = new MergeLog4jConfiguration();
		log4jConfiguration.setApplicationContext(context);
		log4jConfiguration.setPatchLocations(Lists.newArrayList(config1));

	}
}