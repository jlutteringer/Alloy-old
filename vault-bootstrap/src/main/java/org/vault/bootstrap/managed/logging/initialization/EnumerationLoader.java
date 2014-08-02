package org.vault.bootstrap.managed.logging.initialization;

import java.util.List;

import org.alloy.metal.enumeration.ExtendableEnumeration;

public interface EnumerationLoader {
	public List<Class<? extends ExtendableEnumeration>> getEnumerationsToLoad();
}
