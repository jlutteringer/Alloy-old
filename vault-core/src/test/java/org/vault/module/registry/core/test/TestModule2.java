package org.vault.module.registry.core.test;

import org.springframework.stereotype.Component;
import org.vault.core.module.domain.simple.SimpleModule;

@Component
public class TestModule2 extends SimpleModule {
	public TestModule2() {
		this.name = "vault-core-test-module2";
		this.friendlyName = "Test Module 2";
	}
}
