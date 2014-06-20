package org.vault.core.test;

import org.junit.Test;
import org.vault.bootstrap.VaultApplicationBootstrapper;
import org.vault.bootstrap.context.VaultApplicationContext;
import org.vault.core.context.XmlVaultApplicationContext;
import org.vault.core.test.bootstrap.AbstractConfigurationTest;

import com.google.common.collect.Lists;

public class MergeEnvironmentLoggingPropertiesTest extends AbstractConfigurationTest {
	@Test
	public void testEnvironmentLoggingPropertiesMerge() {
		VaultApplicationBootstrapper bootstrap = new VaultApplicationBootstrapper(XmlVaultApplicationContext::new);
		bootstrap.setBootstrapConfigurationLocations(Lists.newArrayList("vault-test-bootstrap-applicationContext.xml"));
		VaultApplicationContext context = bootstrap.bootstrap();
		context.refresh();
		context.start();
	}
}
