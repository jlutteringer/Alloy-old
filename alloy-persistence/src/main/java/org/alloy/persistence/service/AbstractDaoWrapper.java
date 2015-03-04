package org.alloy.persistence.service;

import java.util.Optional;

import org.alloy.metal.collections.list.AList;
import org.alloy.metal.domain.Identifiable;
import org.alloy.persistence.dao.Dao;
import org.alloy.persistence.dao.DaoFacade;
import org.alloy.persistence.utilities.QueryQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class AbstractDaoWrapper<T extends Identifiable, N extends Dao<T>> implements DaoFacade<T> {
	@Autowired
	protected N dao;

	@Override
	public T create() {
		return dao.create();
	}

	@Override
	public Optional<T> find(long id) {
		return dao.find(id);
	}

	@Override
	public AList<T> findAll() {
		return dao.findAll();
	}

	@Override
	public AList<T> findAll(QueryQualifier qualifier) {
		return dao.findAll(qualifier);
	}

	@Override
	@Transactional("primary")
	public T save(T entity) {
		return dao.save(entity);
	}

	@Override
	public void delete(T entity) {
		dao.delete(entity);
	}
}
