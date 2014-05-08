package org.vault.core.test;

import org.junit.Test;
import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.vault.bootstrap.CoreApplicationBootstrapper;
import org.vault.core.test.bootstrap.AbstractConfigurationTest;

import com.google.common.collect.Lists;

public class MergeEnvironmentLoggingPropertiesTest extends AbstractConfigurationTest {
	@Test
	public void testEnvironmentLoggingPropertiesMerge() {
		CoreApplicationBootstrapper bootstrap = new CoreApplicationBootstrapper();
		bootstrap.setBootstrapConfigurationLocation(Lists.newArrayList("vault-test-bootstrap-applicationContext.xml"));
		AbstractRefreshableApplicationContext context = bootstrap.bootstrap();
		context.refresh();
		context.start();
	}
}
