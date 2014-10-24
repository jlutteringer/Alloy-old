package org.alloy.log.domain;

import org.alloy.metal.object.DomainObject;
import org.alloy.metal.object.Named;
import org.alloy.metal.object.SimpleClosable;
import org.alloy.metal.object.audit.TimeStamped;

public interface AlloyLog extends DomainObject, Named, TimeStamped, SimpleClosable {
	public boolean isOpen();
}