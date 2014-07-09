package org.vault.persistence.utilities;

public class QueryQualifier {
	private String entityName = "entity";
	private String qualification = null;

	public static final QueryQualifier NULL = new QueryQualifier();

	public String getEntityName() {
		return entityName;
	}

	public boolean hasQualification() {
		if (qualification == null) {
			return false;
		}
		return true;
	}

	public String getQualification() {
		return qualification;
	}
}
