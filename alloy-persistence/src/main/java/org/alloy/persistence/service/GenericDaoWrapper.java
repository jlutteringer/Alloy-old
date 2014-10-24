package org.alloy.persistence.service;

import java.util.List;

import org.alloy.metal.object.Identifiable;
import org.alloy.metal.reflection._Reflection;
import org.alloy.metal.spring.TemplateAlloyBean;
import org.alloy.persistence.dao.DaoFacade;
import org.alloy.persistence.managed.GenericDao;
import org.alloy.persistence.utilities.QueryQualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class GenericDaoWrapper<T extends Identifiable> extends TemplateAlloyBean<T> implements DaoFacade<T> {
	@Autowired
	protected GenericDao dao;

	@Override
	public T create() {
		return _Reflection.construct(this.getEntityClass());
	}

	@Override
	public T find(long id) {
		return dao.find(this.getEntityClass(), id);
	}

	@Override
	public List<T> findAll() {
		return dao.findAll(this.getEntityClass());
	}

	@Override
	public List<T> findAll(QueryQualifier qualifier) {
		return dao.findAll(this.getEntityClass(), qualifier);
	}

	@Override
	@Transactional("primary")
	public T save(T entity) {
		return dao.save(entity);
	}

	@Override
	public void delete(T entity) {
		dao.remove(entity);
	}

	protected Class<T> getEntityClass() {
		return this.runtimeType1;
	}
}