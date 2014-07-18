package org.vault.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.vault.user.domain.Permission;

public class SecurityUtils {
	public static GrantedAuthority createAuthority(Permission permission) {
		return new SimpleGrantedAuthority(permission.toString());
	}
}
