package org.vault.persistence.test;

import org.vault.bootstrap.VaultApplicationBootstrapper;
import org.vault.bootstrap.context.VaultApplicationContext;
import org.vault.core.context.XmlVaultApplicationContext;

import com.google.common.collect.Lists;

public class Tester {
	public static void main(String[] args) throws Exception {
		System.setProperty("environment", "dev");

		VaultApplicationBootstrapper bootstrap = new VaultApplicationBootstrapper(XmlVaultApplicationContext::new);
		bootstrap.setBootstrapConfigurationLocations(Lists.newArrayList("vault-bootstrap-applicationContext.xml"));
		VaultApplicationContext context = bootstrap.bootstrap();
		bootstrap.stop();

		context.refresh();
		context.start();
	}
}