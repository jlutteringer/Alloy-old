package org.alloy.user.domain;

import org.alloy.metal.domain.DomainObject;
import org.alloy.metal.object.Describeable;

public interface Permission extends DomainObject, Describeable {
	public PermissionType getType();

	public void setType(PermissionType type);
}
