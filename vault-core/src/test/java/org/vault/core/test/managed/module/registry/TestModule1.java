package org.vault.core.test.managed.module.registry;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.SimpleModule;

@Component
public class TestModule1 extends SimpleModule {
	public TestModule1() {
		this.name = "vault-core-test-module1";
		this.friendlyName = "Test Module 1";
	}
}