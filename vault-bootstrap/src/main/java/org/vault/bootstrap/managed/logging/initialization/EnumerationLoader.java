package org.vault.bootstrap.managed.logging.initialization;

import java.util.List;

import org.vault.base.enumeration.AoEnumeration;

public interface EnumerationLoader {
	public List<Class<? extends AoEnumeration>> getEnumerationsToLoad();
}
