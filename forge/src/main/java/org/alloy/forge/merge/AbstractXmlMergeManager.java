package org.alloy.forge.merge;

import java.util.List;

import org.alloy.metal.configuration.ConfigurationLocation;
import org.alloy.metal.configuration._Configuration;
import org.alloy.metal.resource.ResourceInputStream;
import org.alloy.metal.spring.AbstractLoggingBean;
import org.alloy.metal.xml.merge.MergeXmlConfigResource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

public abstract class AbstractXmlMergeManager extends AbstractLoggingBean implements MergeManager, ApplicationContextAware {
	protected ApplicationContext applicationContext;

	@Override
	public Resource getMergedResource(List<ConfigurationLocation> patchLocations) {
		logger.debug("Unresolved locations: " + patchLocations);
		List<ResourceInputStream> configurations = _Configuration.getConfigurations(patchLocations, applicationContext);
		logger.debug("Resolved locations: " + configurations);

		MergeXmlConfigResource foo = new MergeXmlConfigResource(this.getMergeDescriptionLocation(), applicationContext);
		Resource mergedResource = foo.getMergedConfigResource(configurations);
		return mergedResource;
	}

	protected abstract String getMergeDescriptionLocation();

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
