package org.alloy.domain.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.alloy.metal.object.Named;

@MappedSuperclass
public abstract class NamedTimeStampedEntity extends TimeStampedEntity implements Named {
	private static final long serialVersionUID = -6913799584461018681L;

	@Column(name = "name", nullable = false)
	private String name;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
}
