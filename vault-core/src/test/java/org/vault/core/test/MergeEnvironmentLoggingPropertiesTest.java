package org.vault.core.test;

import org.junit.Test;
import org.vault.core.bootstrap.CoreApplicationBootstrapper;
import org.vault.core.test.bootstrap.AbstractConfigurationTest;
import org.vault.extensibility.context.MergeApplicationContext;

import com.google.common.collect.Lists;

public class MergeEnvironmentLoggingPropertiesTest extends AbstractConfigurationTest {
	@Test
	public void testEnvironmentLoggingPropertiesMerge() {
		CoreApplicationBootstrapper bootstrap = new CoreApplicationBootstrapper();
		bootstrap.setBootstrapConfigurationLocation(Lists.newArrayList("vault-test-bootstrap-applicationContext.xml"));
		MergeApplicationContext context = bootstrap.bootstrap();
		context.refresh();
		context.start();
	}
}
