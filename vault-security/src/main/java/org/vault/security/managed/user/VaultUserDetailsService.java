package org.vault.security.managed.user;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.vault.security.VaultUserDetails;
import org.vault.user.domain.User;
import org.vault.user.managed.service.UserService;

@Component("vaultUserDetailsService")
public class VaultUserDetailsService implements UserDetailsService {
	@Resource
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user found for username: [" + username + "]");
		}

		return new VaultUserDetails(user);
	}
}