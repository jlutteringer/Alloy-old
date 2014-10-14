package org.alloy.persistence.utilities;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class _Query {
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
		if (qualifier.hasQualification()) {
			qualifier.getParameters().forEach(query::setParameter);
		}

		return new QueryWrapper<T>(query);
	}

	public static QueryQualifier where(String query) {
		return where(null, query);
	}

	public static QueryQualifier where(String entityName, String qualification) {
		return new QueryQualifier().setEntityName(entityName).setQualification(qualification);
	}
}