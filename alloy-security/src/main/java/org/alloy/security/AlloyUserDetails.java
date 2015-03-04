package org.alloy.security;

import java.util.Collection;

import org.alloy.metal.collections.set._Sets;
import org.alloy.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AlloyUserDetails implements UserDetails {
	private static final long serialVersionUID = 1701222676432874215L;
	private User user;

	public AlloyUserDetails(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return _Sets.wrap(user.getPermissions())
				.map(_Security::createAuthority)
				.collectList()
				.asCollection();
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