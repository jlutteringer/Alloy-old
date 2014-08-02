package org.vault.base.enumeration;

import org.vault.base.utilities.VOptional;
import org.vault.base.utilities.object.VObjects;

public abstract class AbstractAoEnumeration implements AoEnumeration {
	private String type;
	private String friendlyType;

	public AbstractAoEnumeration() {

	}

	protected void setType(String type) {
		this.type = type;
	}

	protected void setFriendlyType(String friendlyType) {
		this.friendlyType = friendlyType;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getFriendlyType() {
		return friendlyType;
	}

	@Override
	public int hashCode() {
		return VObjects.hashCode(type);
	}

	@Override
	public boolean equals(Object obj) {
		VOptional<Boolean> equals = VObjects.baseEquals(this, obj, true);
		if (equals.isPresent()) {
			return equals.get();
		}

		AbstractAoEnumeration other = (AbstractAoEnumeration) obj;
		if (!this.getType().equals(other.getType())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return this.getType();
	}
}
