package org.vault.module.registry.core.test;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

@Component
public class TestModule2 extends ManagedModule {
	public TestModule2() {
		this.name = "vault-core-test-module2";
		this.friendlyName = "Test Module 2";
	}
}
