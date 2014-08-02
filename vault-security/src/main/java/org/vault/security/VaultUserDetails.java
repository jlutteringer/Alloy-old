package org.vault.security;

import java.util.Collection;

import org.alloy.metal.collections.lists._Lists;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.vault.user.domain.User;

public class VaultUserDetails implements UserDetails {
	private static final long serialVersionUID = 1701222676432874215L;
	private User user;

	public VaultUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return _Lists.transform(user.getPermissions(), SecurityUtils::createAuthority);
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
		// FUTURE Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// FUTURE Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// FUTURE Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// FUTURE Auto-generated method stub
		return true;
	}
}