package org.vault.base.enumeration;

import org.vault.base.utilities.object.VObjects;

import com.google.common.base.Optional;

public abstract class AbstractVEnumeration implements VEnumeration {
	private String type;
	private String friendlyType;

	public AbstractVEnumeration() {

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
		Optional<Boolean> equals = VObjects.baseEquals(this, obj);
		if (equals.isPresent()) {
			return equals.get();
		}

		AbstractVEnumeration other = (AbstractVEnumeration) obj;
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
