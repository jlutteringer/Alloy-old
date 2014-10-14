package org.alloy.persistence.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.alloy.metal.object.Identifiable;
import org.alloy.metal.reflection._Reflection;
import org.alloy.metal.spring.TemplateAlloyBean;
import org.alloy.persistence.managed.entities.EntityManagerContext;
import org.alloy.persistence.utilities.EntityManagers;
import org.alloy.persistence.utilities._Query;
import org.alloy.persistence.utilities.QueryQualifier;
import org.springframework.beans.factory.annotation.Autowired;

public class AbstractDao<T extends Identifiable> extends TemplateAlloyBean<T> implements Dao<T> {
	@Autowired
	private EntityManagerContext context;

	@Override
	public T create() {
		return _Reflection.construct(this.getEntityClassInternal());
	}

	@Override
	public T find(Long id) {
		return getEntityManager().find(this.getEntityClassInternal(), id);
	}

	@Override
	public List<T> findAll() {
		return _Query.select(this.getEntityClassInternal(), getEntityManager()).getResults();
	}

	@Override
	public List<T> findAll(QueryQualifier qualifier) {
		return _Query.select(this.getEntityClassInternal(), qualifier, getEntityManager()).getResults();
	}

	@Override
	public T save(T entity) {
		return getEntityManager().merge(entity);
	}

	@Override
	public void delete(T entity) {
		getEntityManager().remove(entity);
	}

	protected EntityManager getEntityManager() {
		return EntityManagers.findEmForClass(context.getAllEntityManagers(), this.getEntityClassInternal());
	}

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClassInternal() {
		return (Class<T>) this.getEntityClass();
	}

	protected Class<?> getEntityClass() {
		return this.runtimeType1;
	}
}