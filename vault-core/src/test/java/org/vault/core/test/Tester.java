package org.vault.core.test;

import org.springframework.context.ApplicationContext;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.core.bootstrap.CoreApplicationBootstrapper;
import org.vault.core.managed.test.TestService;

public class Tester {
	public static void main(String[] args) {
		ApplicationContext applicationContext =
				new CoreApplicationBootstrapper(Configurations.createLocation("vault-bootstrap-applicationContext.xml")).bootstrap();

		applicationContext.getBean(TestService.class).test();
	}
}