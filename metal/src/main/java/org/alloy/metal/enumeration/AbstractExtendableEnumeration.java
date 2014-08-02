package org.alloy.metal.enumeration;

import org.alloy.metal.function.Option;
import org.alloy.metal.object._Object;

public abstract class AbstractExtendableEnumeration implements ExtendableEnumeration {
	private String type;
	private String friendlyType;

	public AbstractExtendableEnumeration() {

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
		return _Object.hashCode(type);
	}

	@Override
	public boolean equals(Object obj) {
		Option<Boolean> equals = _Object.baseEquals(this, obj, true);
		if (equals.isPresent()) {
			return equals.get();
		}

		AbstractExtendableEnumeration other = (AbstractExtendableEnumeration) obj;
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
