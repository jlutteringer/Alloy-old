package org.alloy.security.managed.user;

import javax.annotation.Resource;

import org.alloy.security.AlloyUserDetails;
import org.alloy.user.domain.User;
import org.alloy.user.managed.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component("alloyUserDetailsService")
public class AlloyUserDetailsService implements UserDetailsService {
	@Resource
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("No user found for username: [" + username + "]");
		}

		return new AlloyUserDetails(user);
	}
}