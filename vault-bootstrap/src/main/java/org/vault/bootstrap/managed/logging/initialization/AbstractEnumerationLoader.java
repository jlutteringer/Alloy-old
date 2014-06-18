package org.vault.bootstrap.managed.logging.initialization;

import java.util.List;

import org.vault.base.enumeration.VEnumeration;
import org.vault.base.spring.beans.AbstractVaultBean;

import com.google.common.collect.Lists;

public abstract class AbstractEnumerationLoader extends AbstractVaultBean implements EnumerationLoader {
	@Override
	public List<Class<? extends VEnumeration>> getEnumerationsToLoad() {
		List<Class<? extends VEnumeration>> registry = Lists.newArrayList();
		this.registerEnumerations(registry);
		return registry;
	}

	protected abstract void registerEnumerations(List<Class<? extends VEnumeration>> registry);
}
