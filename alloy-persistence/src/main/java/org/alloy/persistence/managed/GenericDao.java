package org.alloy.persistence.managed;

import java.util.List;

import javax.persistence.EntityManager;

import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.persistence.managed.entities.EntityManagerContext;
import org.alloy.persistence.utilities.EntityManagers;
import org.alloy.persistence.utilities.QueryQualifier;
import org.alloy.persistence.utilities._Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GenericDao {
	@Autowired
	private EntityManagerContext context;

	public <T> T find(Class<T> entityClass, Long id) {
		return this.getEntityManager(entityClass).find(entityClass, id);
	}

	public <T> List<T> findAll(Class<T> entityClass) {
		return _Query.select(entityClass, this.getEntityManager(entityClass)).getResults();
	}

	public <T> T find(Class<T> entityClass, QueryQualifier qualifier) {
		return _Iterable.getSingleResult(findAll(entityClass, qualifier), true);
	}

	public <T> List<T> findAll(Class<T> entityClass, QueryQualifier qualifier) {
		return _Query.select(entityClass, qualifier, getEntityManager(entityClass)).getResults();
	}

	public <T> T save(T entity) {
		return this.getEntityManager(entity.getClass()).merge(entity);
	}

	public void remove(Object entity) {
		this.getEntityManager(entity.getClass()).remove(entity);
	}

	private EntityManager getEntityManager(Class<?> entityClass) {
		return EntityManagers.findEmForClass(context.getAllEntityManagers(), entityClass);
	}
}
