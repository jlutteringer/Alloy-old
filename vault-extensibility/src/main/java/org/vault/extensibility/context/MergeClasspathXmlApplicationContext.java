package org.vault.extensibility.context;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.vault.extensibility.context.merge.ImportProcessor;
import org.vault.extensibility.context.merge.exceptions.MergeException;

import com.google.common.collect.Lists;

public class MergeClasspathXmlApplicationContext extends ClassPathXmlApplicationContext {
	private static final Log LOG = LogFactory.getLog(MergeClasspathXmlApplicationContext.class);

	private List<String> patchLocations;
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
		List<ResourceInputStream> configurations = Lists.newArrayList();
		for (String configurationLocation : patchLocations) {
			ResourceInputStream stream;

			if (configurationLocation.startsWith("classpath")) {
				InputStream is = MergeClasspathXmlApplicationContext.class.getClassLoader()
						.getResourceAsStream(configurationLocation.substring("classpath*:".length(), configurationLocation.length()));

				stream = new ResourceInputStream(is, configurationLocation);
			} else {
				Resource resource = getResourceByPath(configurationLocation);
				stream = new ResourceInputStream(resource.getInputStream(), configurationLocation);
			}

			if (stream.available() <= 0) {
				stream.close();
				throw new IOException("Unable to open an input stream on specified application context resource: " + configurationLocation);
			}

			configurations.add(stream);
		}

		ImportProcessor importProcessor = new ImportProcessor(this);
		try {
			configurations = importProcessor.extract(configurations);
		} catch (MergeException e) {
			throw new FatalBeanException("Unable to merge source and patch locations", e);
		}

		List<Resource> resources = new MergeApplicationContextXmlConfigResource().getConfigResources(configurations);

		reader.loadBeanDefinitions(resources.toArray(new Resource[0]));
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
	public List<String> getPatchLocations() {
		return patchLocations;
	}

	/**
	* @param patchLocation the patchLocation to set
	*/
	public void setPatchLocations(List<String> configurationLocations) {
		this.patchLocations = configurationLocations;
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
