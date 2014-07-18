package org.vault.user.domain;

import org.vault.base.domain.Describeable;
import org.vault.base.domain.DomainObject;

public interface Permission extends DomainObject, Describeable {
	public PermissionType getType();

	public void setType(PermissionType type);
}
