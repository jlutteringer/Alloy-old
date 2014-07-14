package org.vault.user.managed.dao;

import org.springframework.stereotype.Repository;
import org.vault.persistence.dao.AbstractDao;
import org.vault.user.domain.User;
import org.vault.user.domain.UserImpl;

@Repository
public class UserDao extends AbstractDao<User> {
	@Override
	protected Class<?> getEntityClass() {
		return UserImpl.class;
	}
}