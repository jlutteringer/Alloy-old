package org.alloy.domain.information;

import org.alloy.metal.domain.DomainObject;
import org.alloy.metal.object.Activatable;
import org.alloy.metal.object.Defaultable;

public interface Phone extends DomainObject, Activatable, Defaultable {
	public String getPhoneNumber();

	public void setPhoneNumber(String phoneNumber);
}
