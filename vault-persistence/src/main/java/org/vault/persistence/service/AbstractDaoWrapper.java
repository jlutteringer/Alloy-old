package org.vault.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.vault.base.domain.Identifiable;
import org.vault.persistence.dao.Dao;
import org.vault.persistence.dao.DaoFacade;
import org.vault.persistence.utilities.QueryQualifier;

public class AbstractDaoWrapper<T extends Identifiable, N extends Dao<T>> implements DaoFacade<T> {
	@Autowired
	protected N dao;

	@Override
	public T find(Long id) {
		return dao.find(id);
	}

	@Override
	public List<T> findAll() {
		return dao.findAll();
	}

	@Override
	public List<T> findAll(QueryQualifier qualifier) {
		return dao.findAll(qualifier);
	}

	@Override
	public T save(T entity) {
		return dao.save(entity);
	}

	@Override
	public void delete(T entity) {
		dao.delete(entity);
	}
}
