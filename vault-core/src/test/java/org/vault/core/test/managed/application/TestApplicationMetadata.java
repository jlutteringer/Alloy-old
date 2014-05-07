package org.vault.core.test.managed.application;

import org.springframework.stereotype.Component;
import org.vault.base.application.ApplicationMetadata;
import org.vault.base.enviornment.Environment;
import org.vault.base.enviornment.EnvironmentType;

@Component
public class TestApplicationMetadata implements ApplicationMetadata {
	@Override
	public Environment getEnvironment() {
		Environment environment = new Environment();
		environment.setType(EnvironmentType.DEV);
		return environment;
	}
}
