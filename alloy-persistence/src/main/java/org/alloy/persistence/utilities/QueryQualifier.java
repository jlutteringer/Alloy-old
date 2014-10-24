package org.alloy.persistence.utilities;

import java.util.Map;

import org.alloy.metal.string._String;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Maps;

public class QueryQualifier {
	private String entityName = "entity";
	private String qualification = null;
	private Map<String, Object> parameters = Maps.newHashMap();
	private int limit = -1;

	public static final QueryQualifier NULL = new QueryQualifier();

	public String getEntityName() {
		return entityName;
	}

	public QueryQualifier setEntityName(String entityName) {
		if (!StringUtils.isBlank(entityName)) {
			this.entityName = entityName;
		}
		return this;
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

	public int getLimit() {
		return limit;
	}

	public QueryQualifier setQualification(String qualification) {
		this.qualification = qualification;
		return this;
	}

	public QueryQualifier setParameter(String name, Object value) {
		parameters.put(name, value);
		return this;
	}

	public QueryQualifier nextParameter(Object value) {
		if (!this.hasQualification()) {
			throw new RuntimeException("Can't set next parameter without a query!");
		}

		parameters.put(_String.substringBetween(qualification, ":", " ", parameters.size()), value);
		return this;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public QueryQualifier limit(int limit) {
		this.limit = limit;
		return this;
	}
}
