package org.vault.boot.test;

import org.vault.boot.application.VaultApplication;

public class Test {
	public static void main(String[] args) {
		System.setProperty("environment", "dev");
		VaultApplication.run(args);
	}
}
