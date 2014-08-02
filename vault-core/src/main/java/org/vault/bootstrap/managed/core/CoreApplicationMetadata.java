package org.vault.bootstrap.managed.core;

import org.alloy.metal.application.ApplicationMetaData;
import org.alloy.metal.enumeration._ExtendableEnumeration;
import org.alloy.metal.environment.Environment;
import org.alloy.metal.environment.EnvironmentType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CoreApplicationMetadata implements ApplicationMetaData {
	private Environment environment;

	@Value("${environment}")
	public void setEnvironment(String environmentString) {
		this.environment = new Environment();
		this.environment.setType(_ExtendableEnumeration.getInstance(environmentString.toUpperCase(), EnvironmentType.class));
	}

	@Override
	public Environment getEnvironment() {
		return environment;
	}
}
