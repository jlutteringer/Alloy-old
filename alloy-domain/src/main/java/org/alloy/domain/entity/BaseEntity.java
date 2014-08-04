package org.alloy.domain.entity;

import org.alloy.metal.object.DomainObject;

public abstract class BaseEntity implements DomainObject {
	private static final long serialVersionUID = 5994413883031694100L;

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + ":[" + this.getId() + "]";
	}
}