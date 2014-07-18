package org.vault.user.domain;

import java.util.Set;

import org.vault.base.domain.Describeable;
import org.vault.base.domain.DomainObject;

public interface Role extends DomainObject, Describeable {
	public Set<Permission> getPermissions();
}