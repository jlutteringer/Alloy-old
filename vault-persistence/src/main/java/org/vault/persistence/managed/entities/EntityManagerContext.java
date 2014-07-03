package org.vault.persistence.managed.entities;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class EntityManagerContext {
	@PersistenceContext(unitName = "primary")
	private EntityManager primary;

	public Iterable<EntityManager> getAllEntityManagers() {
		return Arrays.asList(primary);
	}
}