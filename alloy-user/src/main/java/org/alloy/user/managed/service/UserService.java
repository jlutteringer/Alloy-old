package org.alloy.user.managed.service;

import java.util.Optional;

import org.alloy.persistence.service.AbstractDaoWrapper;
import org.alloy.persistence.utilities._Query;
import org.alloy.user.domain.User;
import org.alloy.user.managed.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractDaoWrapper<User, UserDao> {
	public Optional<User> findByUsername(String username) {
		return dao.find(_Query.where("entity.username = :username").setParameter("username", username));
	}
}