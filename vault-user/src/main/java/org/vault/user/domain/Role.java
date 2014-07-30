package org.vault.user.domain;

import java.util.Set;

import org.vault.base.object.Describeable;
import org.vault.base.object.DomainObject;

public interface Role extends DomainObject, Describeable {
	public Set<Permission> getPermissions();
}