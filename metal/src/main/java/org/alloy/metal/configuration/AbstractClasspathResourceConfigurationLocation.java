package org.alloy.metal.configuration;

import java.util.List;

import org.alloy.metal.collections.directory.Directory;
import org.alloy.metal.collections.directory.DirectoryEntry;
import org.alloy.metal.collections.directory._Directory;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.resource.ResourceInputStream;
import org.alloy.metal.resource._Resource;
import org.springframework.context.ApplicationContext;

public abstract class AbstractClasspathResourceConfigurationLocation extends AbstractConfigurationLocation implements ClasspathResourceConfigurationLocation {
	@Override
	public Directory<String, ResourceInputStream> resolveResources(ApplicationContext context) {
		Directory<String, ResourceInputStream> directory = _Directory.newDirectory();

		for (DirectoryEntry<String, String> entry : this.getResourceLocationDirectory().getEntries()) {
			List<ResourceInputStream> streams = this.resolveResouceLocations(entry.getValue(), context);
			for (ResourceInputStream stream : streams) {
				directory.put(entry.getKey(), stream);
			}
		}

		return directory;
	}

	@Override
	public List<ResourceInputStream> resolveResouceLocations(String resourceLocation, ApplicationContext context) {
		return _List.transform(_Resource.getResources(resourceLocation, context), ResourceInputStream.transformer());
	}

	@Override
	public String toString() {
		return this.getResourceLocationDirectory().toString();
	}
}
