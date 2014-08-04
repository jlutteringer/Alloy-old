package org.alloy.bootstrap.managed.user.enumerations;

import java.util.List;

import org.alloy.forge.enumeration.AbstractEnumerationLoader;
import org.alloy.metal.enumeration.ExtendableEnumeration;
import org.alloy.user.domain.PermissionType;
import org.alloy.user.domain.UserType;

public class UserEnumerationLoader extends AbstractEnumerationLoader {
	@Override
	protected void registerEnumerations(List<Class<? extends ExtendableEnumeration>> registry) {
		registry.add(PermissionType.class);
		registry.add(UserType.class);
	}
}