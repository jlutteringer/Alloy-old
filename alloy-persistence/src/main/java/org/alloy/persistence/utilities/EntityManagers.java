package org.alloy.persistence.utilities;

import javax.persistence.EntityManager;

public class EntityManagers {
	public static EntityManager findEmForClass(Iterable<EntityManager> potentialEms, Class<?> clazz) {
		for (EntityManager em : potentialEms) {
			try {
				em.getMetamodel().managedType(clazz);
				return em;
			} catch (IllegalArgumentException e) {
				// Do nothing
			}
		}

		throw new RuntimeException("Unable to location persistence unit for class " + clazz.getName());
	}
}