package org.alloy.metal.object;

public interface Flagged {
	public boolean isDefault();

	public void setDefault(boolean isDefault);

	public boolean isActive();

	public void setActive(boolean isActive);
}
