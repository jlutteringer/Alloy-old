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

public class GenericDaoWrapper<T extends Identifiable, N extends T> extends TemplateAlloyBean<N> implements DaoFacade<T> {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		return (List<T>) dao.findAll(this.getEntityClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(QueryQualifier qualifier) {
		return (List<T>) dao.findAll(this.getEntityClass(), qualifier);
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

	protected Class<N> getEntityClass() {
		return this.runtimeType1;
	}
}