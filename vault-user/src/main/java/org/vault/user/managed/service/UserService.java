package org.vault.user.managed.service;

import org.springframework.stereotype.Service;
import org.vault.persistence.service.AbstractDaoWrapper;
import org.vault.persistence.utilities.Queries;
import org.vault.user.domain.User;
import org.vault.user.managed.dao.UserDao;

@Service
public class UserService extends AbstractDaoWrapper<User, UserDao> {
	public User findByUsername(String username) {
		return dao.find(Queries.where("entity.username = :username").setParameter("username", username));
	}
}