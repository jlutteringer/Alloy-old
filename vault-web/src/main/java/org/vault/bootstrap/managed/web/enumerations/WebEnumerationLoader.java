package org.vault.bootstrap.managed.web.enumerations;

import java.util.List;

import org.vault.base.enumeration.AoEnumeration;
import org.vault.bootstrap.managed.logging.initialization.AbstractEnumerationLoader;
import org.vault.site.managed.request.RequestLifecycle;

public class WebEnumerationLoader extends AbstractEnumerationLoader {
	@Override
	protected void registerEnumerations(List<Class<? extends AoEnumeration>> registry) {
		registry.add(RequestLifecycle.class);
	}
}