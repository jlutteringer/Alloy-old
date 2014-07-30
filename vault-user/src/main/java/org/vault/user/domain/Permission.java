package org.vault.user.domain;

import org.vault.base.object.Describeable;
import org.vault.base.object.DomainObject;

public interface Permission extends DomainObject, Describeable {
	public PermissionType getType();

	public void setType(PermissionType type);
}
