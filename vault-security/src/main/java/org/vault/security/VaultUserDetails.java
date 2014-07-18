package org.vault.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.vault.base.collections.lists.VLists;
import org.vault.user.domain.User;

public class VaultUserDetails implements UserDetails {
	private static final long serialVersionUID = 1701222676432874215L;
	private User user;

	public VaultUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return VLists.transform(user.getPermissions(), SecurityUtils::createAuthority);
	}

	@Override
	public String getPassword() {
		return user.getAuthentication();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}