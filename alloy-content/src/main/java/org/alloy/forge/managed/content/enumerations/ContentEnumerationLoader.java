package org.alloy.forge.managed.content.enumerations;

import java.util.List;

import org.alloy.content.domain.ContentFieldType;
import org.alloy.content.domain.ContentRelationType;
import org.alloy.forge.enumeration.AbstractEnumerationLoader;
import org.alloy.metal.enumeration.ExtendableEnumeration;

public class ContentEnumerationLoader extends AbstractEnumerationLoader {
	@Override
	protected void registerEnumerations(List<Class<? extends ExtendableEnumeration>> registry) {
		registry.add(ContentFieldType.class);
		registry.add(ContentRelationType.class);
	}
}