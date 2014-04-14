package org.vault.base.enumeration;


public class AbstractVEnumeration implements VEnumeration {
	private String type;
	private String friendlyType;

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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FulfillmentType other = (FulfillmentType) obj;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
