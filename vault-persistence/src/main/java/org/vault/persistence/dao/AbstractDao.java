package org.vault.persistence.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.vault.base.domain.Identifiable;
import org.vault.base.spring.beans.VaultBeanT1;
import org.vault.persistence.utilities.EntityManagers;
import org.vault.persistence.utilities.Queries;
import org.vault.persistence.utilities.QueryQualifier;

public class AbstractDao<T extends Identifiable> extends VaultBeanT1<T> implements Dao<T> {
	// TODO
//	@Autowired
	private List<EntityManager> potentialEms;

	private EntityManager em;

	@PostConstruct
	private void initialize() {
		em = EntityManagers.findEmForClass(potentialEms, this.runtimeType1);
	}

	@Override
	public T find(Long id) {
		return em.find(this.runtimeType1, id);
	}

	@Override
	public List<T> findAll() {
		return Queries.select(this.runtimeType1, em).getResults();
	}

	@Override
	public List<T> findAll(QueryQualifier qualifier) {
		return Queries.select(this.runtimeType1, qualifier, em).getResults();
	}

	@Override
	public T save(T entity) {
		return em.merge(entity);
	}

	@Override
	public void delete(T entity) {
		em.remove(entity);
	}
}