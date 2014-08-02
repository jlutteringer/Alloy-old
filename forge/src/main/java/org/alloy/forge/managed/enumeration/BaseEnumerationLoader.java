package org.alloy.forge.managed.enumeration;

import java.util.List;

import org.alloy.forge.enumeration.AbstractEnumerationLoader;
import org.alloy.metal.enumeration.ExtendableEnumeration;
import org.alloy.metal.environment.EnvironmentType;
import org.alloy.metal.order.Phase;
import org.alloy.metal.system.SystemPropertyFieldType;
import org.springframework.stereotype.Component;

@Component
public class BaseEnumerationLoader extends AbstractEnumerationLoader {
	@Override
	protected void registerEnumerations(List<Class<? extends ExtendableEnumeration>> registry) {
		registry.add(EnvironmentType.class);
		registry.add(SystemPropertyFieldType.class);
		registry.add(Phase.class);
	}
}