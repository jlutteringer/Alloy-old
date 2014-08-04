package org.alloy.user.domain;

import java.util.Set;

import org.alloy.metal.object.Describeable;
import org.alloy.metal.object.DomainObject;

public interface Role extends DomainObject, Describeable {
	public Set<Permission> getPermissions();
}