package org.alloy.metal.object;

import org.alloy.metal.string._String;

public abstract class ToStringObject {
	@Override
	public String toString() {
		return _String.stringify(this).useReflection().toString();
	}
}