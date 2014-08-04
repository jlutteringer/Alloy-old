package org.alloy.user.managed.service;

import org.alloy.persistence.service.AbstractDaoWrapper;
import org.alloy.persistence.utilities.Queries;
import org.alloy.user.domain.User;
import org.alloy.user.managed.dao.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractDaoWrapper<User, UserDao> {
	public User findByUsername(String username) {
		return dao.find(Queries.where("entity.username = :username").setParameter("username", username));
	}
}