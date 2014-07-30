package org.vault.base.object;

import org.vault.base.utilities.string.VStrings;

public abstract class ToStringObject {
	@Override
	public String toString() {
		return VStrings.stringify(this).useReflection().toString();
	}
}