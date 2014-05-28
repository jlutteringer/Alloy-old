package org.vault.bootstrap.managed.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.vault.base.application.ApplicationMetaData;
import org.vault.base.enumeration.VEnumerations;
import org.vault.base.enviornment.Environment;
import org.vault.base.enviornment.EnvironmentType;

@Component
public class CoreApplicationMetadata implements ApplicationMetaData {
	private Environment environment;

	@Value("${environment}")
	public void setEnvironment(String environmentString) {
		this.environment = new Environment();
		this.environment.setType(VEnumerations.getInstance(environmentString.toUpperCase(), EnvironmentType.class));
	}

	@Override
	public Environment getEnvironment() {
		return environment;
	}
}
