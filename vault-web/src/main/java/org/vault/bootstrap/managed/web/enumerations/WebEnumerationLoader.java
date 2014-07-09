package org.vault.bootstrap.managed.web.enumerations;

import java.util.List;

import org.vault.base.enumeration.VEnumeration;
import org.vault.bootstrap.managed.logging.initialization.AbstractEnumerationLoader;
import org.vault.web.request.RequestLifecycle;

public class WebEnumerationLoader extends AbstractEnumerationLoader {
	@Override
	protected void registerEnumerations(List<Class<? extends VEnumeration>> registry) {
		registry.add(RequestLifecycle.class);
	}
}