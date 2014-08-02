package org.vault.user.domain;

import org.alloy.metal.object.Describeable;
import org.alloy.metal.object.DomainObject;

public interface Permission extends DomainObject, Describeable {
	public PermissionType getType();

	public void setType(PermissionType type);
}
