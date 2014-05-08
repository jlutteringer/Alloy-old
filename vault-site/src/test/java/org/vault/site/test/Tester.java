package org.vault.site.test;

import org.springframework.context.support.AbstractRefreshableApplicationContext;
import org.vault.bootstrap.CoreApplicationBootstrapper;
import org.vault.core.managed.test.TestService;

import com.google.common.collect.Lists;

public class Tester {
	public static void main(String[] args) {
		CoreApplicationBootstrapper bootsrap = new CoreApplicationBootstrapper();
		bootsrap.setBootstrapConfigurationLocation(Lists.newArrayList("vault-bootstrap-applicationContext.xml"));

		AbstractRefreshableApplicationContext context = bootsrap.bootstrap();
		context.refresh();
		context.start();
		context.registerShutdownHook();

		context.getBean(TestService.class).test();
	}
}