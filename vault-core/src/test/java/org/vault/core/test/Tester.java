package org.vault.core.test;

import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.vault.bootstrap.CoreApplicationBootstrapper;

import com.google.common.collect.Lists;

public class Tester {
	public static void main(String[] args) throws Exception {
		CoreApplicationBootstrapper bootstrap = new CoreApplicationBootstrapper();
		bootstrap.setBootstrapConfigurationLocation(Lists.newArrayList("vault-test-bootstrap-applicationContext.xml"));
		AbstractRefreshableApplicationContext context = bootstrap.bootstrap();
		bootstrap.stop();

		context.refresh();
		context.start();
	}
}