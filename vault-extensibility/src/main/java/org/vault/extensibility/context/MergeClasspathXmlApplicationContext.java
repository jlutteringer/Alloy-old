package org.vault.extensibility.context;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.vault.extensibility.context.merge.ImportProcessor;
import org.vault.extensibility.context.merge.exceptions.MergeException;

public class MergeClasspathXmlApplicationContext extends ClassPathXmlApplicationContext {
	private static final Log LOG = LogFactory.getLog(MergeClasspathXmlApplicationContext.class);

	private String patchLocation;
	private String shutdownBean;
	private String shutdownMethod;

	/**
	* Load the bean definitions with the given XmlBeanDefinitionReader.
	* <p>The lifecycle of the bean factory is handled by the refreshBeanFactory method;
	* therefore this method is just supposed to load and/or register bean definitions.
	* <p>Delegates to a ResourcePatternResolver for resolving location patterns
	* into Resource instances.
	* @throws org.springframework.beans.BeansException in case of bean registration errors
	* @throws java.io.IOException if the required XML document isn't found
	* @see #refreshBeanFactory
	* @see #getConfigLocations
	* @see #getResources
	* @see #getResourcePatternResolver
	*/
	@Override
	protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws BeansException, IOException {
		String[] broadleafConfigLocations = StandardConfigLocations.retrieveAll(StandardConfigLocations.APPCONTEXTTYPE);

		ArrayList<ResourceInputStream> sources = new ArrayList<ResourceInputStream>(20);
		for (String location : broadleafConfigLocations) {
			InputStream source = MergeClasspathXmlApplicationContext.class.getClassLoader().getResourceAsStream(location);
			if (source != null) {
				sources.add(new ResourceInputStream(source, location));
			}
		}
		ResourceInputStream[] filteredSources = new ResourceInputStream[] {};
		filteredSources = sources.toArray(filteredSources);
		String patchLocation = getPatchLocation();
		String[] patchLocations = StringUtils.tokenizeToStringArray(patchLocation, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
		ResourceInputStream[] patches = new ResourceInputStream[patchLocations.length];
		for (int i = 0; i < patchLocations.length; i++) {
			if (patchLocations[i].startsWith("classpath")) {
				InputStream is = MergeClasspathXmlApplicationContext.class.getClassLoader().getResourceAsStream(patchLocations[i].substring("classpath*:".length(), patchLocations[i].length()));
				patches[i] = new ResourceInputStream(is, patchLocations[i]);
			} else {
				Resource resource = getResourceByPath(patchLocations[i]);
				patches[i] = new ResourceInputStream(resource.getInputStream(), patchLocations[i]);
			}
			if (patches[i] == null || patches[i].available() <= 0) {
				throw new IOException("Unable to open an input stream on specified application context resource: " + patchLocations[i]);
			}
		}

		ImportProcessor importProcessor = new ImportProcessor(this);
		try {
			filteredSources = importProcessor.extract(filteredSources);
			patches = importProcessor.extract(patches);
		} catch (MergeException e) {
			throw new FatalBeanException("Unable to merge source and patch locations", e);
		}

		Resource[] resources = new MergeApplicationContextXmlConfigResource().getConfigResources(filteredSources, patches);

		reader.loadBeanDefinitions(resources);
	}

	/* (non-Javadoc)
	* @see org.springframework.context.support.AbstractApplicationContext#doClose()
	*/
	@Override
	protected void doClose() {
		if (getShutdownBean() != null && getShutdownMethod() != null) {
			try {
				Object shutdownBean = getBean(getShutdownBean());
				Method shutdownMethod = shutdownBean.getClass().getMethod(getShutdownMethod(), new Class[] {});
				shutdownMethod.invoke(shutdownBean, new Object[] {});
			} catch (Throwable e) {
				LOG.error("Unable to execute custom shutdown call", e);
			}
		}
		super.doClose();
	}

	/**
	* @return the patchLocation
	*/
	public String getPatchLocation() {
		return patchLocation;
	}

	/**
	* @param patchLocation the patchLocation to set
	*/
	public void setPatchLocation(String patchLocation) {
		this.patchLocation = patchLocation;
	}

	/**
	* @return the shutdownBean
	*/
	public String getShutdownBean() {
		return shutdownBean;
	}

	/**
	* @param shutdownBean the shutdownBean to set
	*/
	public void setShutdownBean(String shutdownBean) {
		this.shutdownBean = shutdownBean;
	}

	/**
	* @return the shutdownMethod
	*/
	public String getShutdownMethod() {
		return shutdownMethod;
	}

	/**
	* @param shutdownMethod the shutdownMethod to set
	*/
	public void setShutdownMethod(String shutdownMethod) {
		this.shutdownMethod = shutdownMethod;
	}
}
