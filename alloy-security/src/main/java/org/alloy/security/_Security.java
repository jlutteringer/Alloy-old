package org.alloy.security;

import org.alloy.user.domain.Permission;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class _Security {
	public static GrantedAuthority createAuthority(Permission permission) {
		return new SimpleGrantedAuthority(permission.toString());
	}
}
