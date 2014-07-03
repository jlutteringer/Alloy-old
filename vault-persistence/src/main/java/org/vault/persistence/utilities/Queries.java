package org.vault.persistence.utilities;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class Queries {
	public static <T> QueryWrapper<T> select(Class<T> clazz, EntityManager em) {
		return select(clazz, QueryQualifier.NULL, em);
	}

	public static <T> QueryWrapper<T> select(Class<T> clazz, QueryQualifier qualifier, EntityManager em) {
		String queryString = "SELECT " + qualifier.getEntityName() + " FROM " +
				clazz.getName() + " " + qualifier.getEntityName();

		if (qualifier.hasQualification()) {
			queryString = queryString + " WHERE " + qualifier.getQualification();
		}

		TypedQuery<T> query = em.createQuery(queryString, clazz);
		return new QueryWrapper<T>(query);
	}
}
