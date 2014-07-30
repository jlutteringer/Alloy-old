package org.vault.domain.information;

import org.vault.base.object.Activatable;
import org.vault.base.object.Defaultable;
import org.vault.base.object.DomainObject;

public interface Phone extends DomainObject, Activatable, Defaultable {
	public String getPhoneNumber();

	public void setPhoneNumber(String phoneNumber);
}
