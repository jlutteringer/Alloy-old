package org.vault.module.registry.core.test;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.ManagedModule;

@Component
public class TestModule1 extends ManagedModule {
	public TestModule1() {
		this.name = "vault-core-test-module1";
		this.friendlyName = "Test Module 1";
	}
}