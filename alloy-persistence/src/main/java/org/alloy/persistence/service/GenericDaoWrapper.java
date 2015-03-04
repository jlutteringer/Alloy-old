package org.alloy.persistence.service;

import java.util.Optional;

import org.alloy.metal.collections.list.AList;
import org.alloy.metal.domain.Identifiable;
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

	@SuppressWarnings("unchecked")
	@Override
	public Optional<T> find(long id) {
		return (Optional<T>) dao.find(this.getEntityClass(), id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public AList<T> findAll() {
		return (AList<T>) dao.findAll(this.getEntityClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public AList<T> findAll(QueryQualifier qualifier) {
		return (AList<T>) dao.findAll(this.getEntityClass(), qualifier);
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