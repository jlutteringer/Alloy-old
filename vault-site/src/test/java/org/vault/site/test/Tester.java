package org.vault.site.test;

import org.vault.core.bootstrap.AbstractCoreApplicationBootstrapper;

public class Tester {
	public static void main(String[] args) {
		new AbstractCoreApplicationBootstrapper("vault-bootstrap-applicationContext.xml").bootstrap();
	}
}
