package org.alloy.security.managed.user;

import java.util.Optional;
import java.util.function.Supplier;

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
		Optional<User> user = userService.findByUsername(username);

		// Code is broken out this way to prevent a bug with lambdas in eclipse
		Supplier<UsernameNotFoundException> exceptionSupplier =
				() -> new UsernameNotFoundException("No user found for username: [" + username + "]");
		return new AlloyUserDetails(user.orElseThrow(exceptionSupplier));
	}
}