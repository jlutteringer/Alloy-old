package org.vault.bootstrap.managed.logging.initialization;

import java.util.List;

import org.alloy.metal.enumeration.ExtendableEnumeration;
import org.alloy.metal.spring.AlloyBean;

import com.google.common.collect.Lists;

public abstract class AbstractEnumerationLoader extends AlloyBean implements EnumerationLoader {
	@Override
	public List<Class<? extends ExtendableEnumeration>> getEnumerationsToLoad() {
		List<Class<? extends ExtendableEnumeration>> registry = Lists.newArrayList();
		this.registerEnumerations(registry);
		return registry;
	}

	protected abstract void registerEnumerations(List<Class<? extends ExtendableEnumeration>> registry);
}
