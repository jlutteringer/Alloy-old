package org.alloy.forge.enumeration;

import java.util.List;

import org.alloy.metal.collections.lists._Lists;
import org.alloy.metal.enumeration.ExtendableEnumeration;

//FUTURE evauluate genericism
public abstract class AbstractEnumerationLoader implements EnumerationLoader {

	@Override
	public List<Class<? extends ExtendableEnumeration>> getEnumerationsToLoad() {
		List<Class<? extends ExtendableEnumeration>> enumerations = _Lists.list();
		this.registerEnumerations(enumerations);
		return enumerations;
	}

	protected abstract void registerEnumerations(List<Class<? extends ExtendableEnumeration>> registry);
}
