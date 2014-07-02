package org.vault.persistence.managed;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.stereotype.Service;
import org.vault.persistence.managed.configuration.PersistenceMergeContext;

@Service
public class MergePersistenceUnitManager extends DefaultPersistenceUnitManager {
	@Autowired
	private PersistenceMergeContext mergeContext;

	@PostConstruct
	private void init() {

	}
}