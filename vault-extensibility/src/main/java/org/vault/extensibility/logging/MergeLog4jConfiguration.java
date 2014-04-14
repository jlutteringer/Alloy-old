package org.vault.extensibility.logging;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.xml.DOMConfigurator;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.extensibility.PatchableConfiguration;
import org.vault.extensibility.context.merge.MergeXmlConfigResource;

public class MergeLog4jConfiguration implements PatchableConfiguration, ApplicationContextAware {
	private static String MERGE_CONFIG_LOCATION = "logging-merge.properties";

	private List<ConfigurationLocation> patchLocations;
	private ApplicationContext applicationContext;

	public void loadMergedLog4jConfiguration() throws IOException {
		Resource mergedResource = this.getMergedResource();

		DOMConfigurator configurator = new DOMConfigurator();
		configurator.doConfigure(mergedResource.getInputStream(), LogManager.getLoggerRepository());
	}

	public Resource getMergedResource() {
		List<ResourceInputStream> configurations = Configurations.getConfigurations(this.getPatchLocations(), applicationContext);

		MergeXmlConfigResource foo = new MergeXmlConfigResource(MERGE_CONFIG_LOCATION, applicationContext);
		Resource mergedResource = foo.getMergedConfigResource(configurations);
		return mergedResource;
	}

	public List<ConfigurationLocation> getPatchLocations() {
		return patchLocations;
	}

	public void setPatchLocations(List<ConfigurationLocation> patchLocations) {
		this.patchLocations = patchLocations;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
