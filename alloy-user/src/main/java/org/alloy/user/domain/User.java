package org.alloy.user.domain;

import java.util.Set;

import org.alloy.metal.domain.DomainObject;
import org.alloy.metal.object.AdditionalFields;

public interface User extends DomainObject, AdditionalFields {
	@Override
	public Long getId();

	public String getUsername();

	public void setUsername(String username);

	public String getAuthentication();

	public void setAuthentication(String username);

	public UserType getType();

	public void setType(UserType type);

	public Set<Role> getRoles();

	public Set<Permission> getPermissions();
}