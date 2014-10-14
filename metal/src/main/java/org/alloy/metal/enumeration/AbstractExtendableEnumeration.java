package org.alloy.metal.enumeration;

import java.util.Optional;

import org.alloy.metal.object.AbstractFriendlyNamed;
import org.alloy.metal.object._Object;

public abstract class AbstractExtendableEnumeration extends AbstractFriendlyNamed implements ExtendableEnumeration {
	public AbstractExtendableEnumeration() {

	}

	@Override
	public int hashCode() {
		return _Object.hashCode(name);
	}

	@Override
	public boolean equals(Object obj) {
		Optional<Boolean> equals = _Object.baseEquals(this, obj, true);
		if (equals.isPresent()) {
			return equals.get();
		}

		AbstractExtendableEnumeration other = (AbstractExtendableEnumeration) obj;
		if (!this.getName().equals(other.getName())) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return this.getName();
	}
}
