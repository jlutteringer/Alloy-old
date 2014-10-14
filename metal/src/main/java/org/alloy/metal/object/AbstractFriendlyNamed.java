package org.alloy.metal.object;

public class AbstractFriendlyNamed extends AbstractNamed implements FriendlyNamed {
	protected String friendlyName;

	@Override
	public String getFriendlyName() {
		return friendlyName;
	}

	@Override
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}
}