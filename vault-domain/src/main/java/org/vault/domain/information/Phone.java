package org.vault.domain.information;

import org.vault.base.domain.Activatable;
import org.vault.base.domain.Defaultable;
import org.vault.base.domain.DomainObject;

public interface Phone extends DomainObject, Activatable, Defaultable {
	public String getPhoneNumber();

	public void setPhoneNumber(String phoneNumber);
}
