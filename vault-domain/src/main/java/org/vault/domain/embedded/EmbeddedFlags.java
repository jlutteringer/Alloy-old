package org.vault.domain.embedded;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.vault.base.object.Flagged;

@Embeddable
public class EmbeddedFlags implements Flagged {
	@Column(name = "DEFAULT", nullable = false)
	private boolean isDefault = true;

	@Column(name = "ACTIVE", nullable = false)
	private boolean isActive = true;

	@Override
	public boolean isDefault() {
		return isDefault;
	}

	@Override
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public boolean isActive() {
		return isActive;
	}

	@Override
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
