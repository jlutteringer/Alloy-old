package org.vault.core.test;

import org.vault.base.utilities.configuration.Configurations;
import org.vault.core.bootstrap.CoreApplicationBootstrapper;

public class Tester {
	public static void main(String[] args) {
		new CoreApplicationBootstrapper(Configurations.createLocation("vault-bootstrap-applicationContext.xml")).bootstrap();
	}
}