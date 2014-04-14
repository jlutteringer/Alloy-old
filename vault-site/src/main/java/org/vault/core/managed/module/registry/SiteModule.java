package org.vault.core.managed.module.registry;

import org.springframework.stereotype.Component;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.core.module.domain.simple.ApplicationModule;

@Component
public class SiteModule extends ApplicationModule {
	public SiteModule() {
		configurationLocations.add(Configurations.createClasspathLocation("myAppContext.xml"));
	}
}
