package org.vault.extensibility.context;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.vault.base.utilities.configuration.ConfigurationLocation;

public class MergeClasspathXmlApplicationContext extends ClassPathXmlApplicationContext implements MergeApplicationContext {
	private List<ConfigurationLocation> patchLocations;
	private MergeApplicationContextBeanDefinitionLoader loader = new MergeApplicationContextBeanDefinitionLoader(this);

	@Override
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
		loader.loadBeanDefinitions(reader);
	}

	public List<ConfigurationLocation> getPatchLocations() {
		return patchLocations;
	}

	public void setPatchLocations(List<ConfigurationLocation> configurationLocations) {
		this.patchLocations = configurationLocations;
	}
}
