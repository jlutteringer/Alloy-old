package org.alloy.persistence.dao;

import java.util.List;

import org.alloy.metal.object.Identifiable;
import org.alloy.metal.reflection._Reflection;
import org.alloy.metal.spring.TemplateAlloyBean;
import org.alloy.persistence.managed.GenericDao;
import org.alloy.persistence.utilities.QueryQualifier;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDao<T extends Identifiable> extends TemplateAlloyBean<T> implements Dao<T> {
	@Autowired
	private GenericDao genericDao;

	@Override
	public T create() {
		return _Reflection.construct(this.getEntityClassInternal());
	}

	@Override
	public T find(Long id) {
		return genericDao.find(this.getEntityClassInternal(), id);
	}

	@Override
	public List<T> findAll() {
		return genericDao.findAll(this.getEntityClassInternal());
	}

	@Override
	public List<T> findAll(QueryQualifier qualifier) {
		return genericDao.findAll(this.getEntityClassInternal(), qualifier);
	}

	@Override
	public T save(T entity) {
		return genericDao.save(entity);
	}

	@Override
	public void delete(T entity) {
		genericDao.remove(entity);
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClassInternal() {
		return (Class<T>) this.getEntityClass();
	}

	protected Class<?> getEntityClass() {
		return this.runtimeType1;
	}
}