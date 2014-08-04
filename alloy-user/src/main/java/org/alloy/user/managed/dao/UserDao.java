package org.alloy.user.managed.dao;

import org.alloy.persistence.dao.AbstractDao;
import org.alloy.user.domain.User;
import org.alloy.user.domain.UserImpl;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends AbstractDao<User> {
	@Override
	protected Class<?> getEntityClass() {
		return UserImpl.class;
	}
}