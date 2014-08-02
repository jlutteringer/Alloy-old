package org.vault.base.utilities.configuration.classpath;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.vault.base.collections.directory._Directory;
import org.vault.base.collections.directory.Directory;
import org.vault.base.collections.directory.DirectoryEntry;
import org.vault.base.collections.lists._Lists;
import org.vault.base.resource.ResourceInputStream;
import org.vault.base.resource.VResources;
import org.vault.base.utilities.configuration.AbstractConfigurationLocation;

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
		return _Lists.transform(VResources.getResources(resourceLocation, context), ResourceInputStream.transformer());
	}

	@Override
	public String toString() {
		return this.getResourceLocationDirectory().toString();
	}
}
