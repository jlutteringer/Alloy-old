package org.vault.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.alloy.metal.enumeration._ExtendableEnumeration;
import org.vault.domain.entity.DescribedEntity;

@Entity
@Table(name = "permission")
public class PermissionImpl extends DescribedEntity implements Permission {
	private static final long serialVersionUID = 8894456661129976086L;
	private static final String ENTITY_NAME = "permission";

	@Id
	@GeneratedValue(generator = ENTITY_NAME + "Id", strategy = GenerationType.TABLE)
	@TableGenerator(name = ENTITY_NAME + "Id", table = "sequenceGenerator", pkColumnName = "idName", valueColumnName = "idVal", pkColumnValue = ENTITY_NAME, allocationSize = 50)
	@Column(name = ENTITY_NAME + "Id")
	protected Long id;

	@Column(name = "permissionType", nullable = false)
	protected String type;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public PermissionType getType() {
		return _ExtendableEnumeration.getInstance(type, PermissionType.class);
	}

	@Override
	public void setType(PermissionType type) {
		this.type = type.getType();
	}

	@Override
	public String toString() {
		return type + "_" + this.getName();
	}
}