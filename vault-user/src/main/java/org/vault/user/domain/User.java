package org.vault.user.domain;

import java.util.Set;

import org.vault.base.domain.Activatable;
import org.vault.base.domain.AdditionalFields;
import org.vault.base.domain.DomainObject;

public interface User extends DomainObject, AdditionalFields, Activatable {
	@Override
	public Long getId();

	public String getUsername();

	public void setUsername(String username);

	public String getName();

	public void setName(String name);

	public String getLogin();

	public void setLogin(String login);

	public String getPassword();

	public void setPassword(String password);

	public String getEmail();

	public void setEmail(String email);

	public Set<Role> getRoles();

	public void setRoles(Set<Role> roles);

	/**
	* Stores the user's phone number.
	* @param phone
	*/
	public void setPhoneNumber(String phone);

	/**
	* Returns the users phone number.
	* @return
	*/
	public String getPhoneNumber();

	public Set<Permission> getPermissions();

	public void setPermissions(Set<Permission> permissions);
}