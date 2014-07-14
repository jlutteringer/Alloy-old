package org.vault.domain.entity;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MapKeyColumn;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.BatchSize;
import org.vault.base.domain.AdditionalFields;

@MappedSuperclass
public abstract class AdditionalFieldsEntity extends BaseEntity implements AdditionalFields {
	private static final long serialVersionUID = 9002504285095096158L;

	@ElementCollection
	@MapKeyColumn(name = "FIELD_NAME")
	@Column(name = "FIELD_VALUE")
	@BatchSize(size = 50)
	protected Map<String, String> additionalFields = new HashMap<String, String>();
}
