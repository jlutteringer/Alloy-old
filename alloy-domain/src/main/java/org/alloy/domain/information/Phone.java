package org.alloy.domain.information;

import org.alloy.metal.object.Activatable;
import org.alloy.metal.object.Defaultable;
import org.alloy.metal.object.DomainObject;

public interface Phone extends DomainObject, Activatable, Defaultable {
	public String getPhoneNumber();

	public void setPhoneNumber(String phoneNumber);
}
