package org.vault.bootstrap.managed.logging.initialization;

import java.util.List;

import org.vault.base.enumeration.AoEnumeration;
import org.vault.base.spring.beans.VaultBean;

import com.google.common.collect.Lists;

public abstract class AbstractEnumerationLoader extends VaultBean implements EnumerationLoader {
	@Override
	public List<Class<? extends AoEnumeration>> getEnumerationsToLoad() {
		List<Class<? extends AoEnumeration>> registry = Lists.newArrayList();
		this.registerEnumerations(registry);
		return registry;
	}

	protected abstract void registerEnumerations(List<Class<? extends AoEnumeration>> registry);
}
