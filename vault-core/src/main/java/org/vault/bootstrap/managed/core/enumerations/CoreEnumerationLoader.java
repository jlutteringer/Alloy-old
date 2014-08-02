package org.vault.bootstrap.managed.core.enumerations;

import java.util.List;

import org.alloy.metal.enumeration.ExtendableEnumeration;
import org.alloy.metal.environment.EnvironmentType;
import org.alloy.metal.order.Phase;
import org.vault.bootstrap.managed.logging.initialization.AbstractEnumerationLoader;
import org.vault.core.managed.system.properties.domain.SystemPropertyFieldType;

public class CoreEnumerationLoader extends AbstractEnumerationLoader {
	@Override
	protected void registerEnumerations(List<Class<? extends ExtendableEnumeration>> registry) {
		registry.add(EnvironmentType.class);
		registry.add(SystemPropertyFieldType.class);
		registry.add(Phase.class);
	}
}