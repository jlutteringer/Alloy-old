package org.alloy.user.domain;

import java.util.Set;

import org.alloy.metal.domain.DomainObject;
import org.alloy.metal.object.Describeable;

public interface Role extends DomainObject, Describeable {
	public Set<Permission> getPermissions();
}