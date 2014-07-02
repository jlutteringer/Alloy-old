package org.vault.admin.test;

import org.vault.site.boot.VaultApplication;

public class Tester {
	public static void main(String[] args) {
		System.setProperty("environment", "dev");

		VaultApplication.run(args);
	}
}