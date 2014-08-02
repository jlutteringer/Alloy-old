package org.alloy.forge.enumeration;

import java.util.List;

import org.alloy.metal.collections.lists._List;
import org.alloy.metal.enumeration.ExtendableEnumeration;

//TODO evauluate genericism
public abstract class AbstractEnumerationLoader implements EnumerationLoader {

	@Override
	public List<Class<? extends ExtendableEnumeration>> getEnumerationsToLoad() {
		List<Class<? extends ExtendableEnumeration>> enumerations = _List.list();
		this.registerEnumerations(enumerations);
		return enumerations;
	}

	protected abstract void registerEnumerations(List<Class<? extends ExtendableEnumeration>> registry);
}
