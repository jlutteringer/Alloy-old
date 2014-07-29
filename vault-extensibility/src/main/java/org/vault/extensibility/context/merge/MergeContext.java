/*
* #%L
* BroadleafCommerce Common Libraries
* %%
* Copyright (C) 2009 - 2013 Broadleaf Commerce
* %%
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* #L%
*/

package org.vault.extensibility.context.merge;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.vault.base.collections.iterable.VIterables;
import org.vault.base.resource.ResourceInputStream;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.base.utilities.configuration.Configurations;
import org.vault.base.utilities.matcher.AbstractContextualSelector;
import org.vault.base.utilities.matcher.ContextualMatcher;
import org.vault.extensibility.PatchableConfiguration;
import org.vault.extensibility.context.merge.exceptions.MergeException;
import org.vault.extensibility.context.merge.exceptions.MergeManagerSetupException;
import org.vault.extensibility.context.merge.handlers.MergeHandler;
import org.vault.extensibility.context.merge.handlers.MergeMatcherType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.google.common.collect.Lists;

/**
* This class manages all xml merge interactions with callers. It is responsible for
* not only loading the handler configurations, but also for cycling through the handlers
* in a prioritized fashion and exporting the final merged document.
*
* @author jfischer
*
*/
public class MergeContext implements PatchableConfiguration, ApplicationContextAware {
	private static final Log LOG = LogFactory.getLog(MergeContext.class);
	private static DocumentBuilder builder;

	private ContextualMatcher<MergeHandler, String> contextualNameMatcher = new AbstractContextualSelector<MergeHandler, String>() {
		@Override
		public boolean test(MergeHandler input) {
			if (this.context.equals(input.getName())) {
				return true;
			}
			return false;
		}
	};

	static {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			builder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			LOG.error("Unable to create document builder", e);
			throw new RuntimeException(e);
		}
	}

	private ApplicationContext applicationContext;
	private String defaultHandlerConfiguration;
	private List<MergeHandler> handlers = Lists.newArrayList();
	private List<ConfigurationLocation> patchLocations = Lists.newArrayList();

	public MergeContext(String defaultHandlerConfiguration, ApplicationContext applicationContext) throws MergeManagerSetupException {
		this.defaultHandlerConfiguration = defaultHandlerConfiguration;
		this.applicationContext = applicationContext;

		try {
			Properties props = loadProperties();
			removeSkippedMergeComponents(props);
			setHandlers(props);
		} catch (IOException e) {
			throw new MergeManagerSetupException(e);
		} catch (ClassNotFoundException e) {
			throw new MergeManagerSetupException(e);
		} catch (IllegalAccessException e) {
			throw new MergeManagerSetupException(e);
		} catch (InstantiationException e) {
			throw new MergeManagerSetupException(e);
		}
	}

	private void removeSkippedMergeComponents(Properties props) {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("/broadleaf-commmerce/skipMergeComponents.txt");

		if (inputStream != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("mergeClassOverrides file found.");
			}

			final InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			try {
				while (bufferedReader.ready())
				{
					String line = bufferedReader.readLine();
					if (LOG.isDebugEnabled()) {
						LOG.debug("mergeComponentOverrides - overridding " + line);
					}
					removeSkipMergeComponents(props, line);
				}
			} catch (IOException e) {
				LOG.error("Error reading resource - /broadleaf-commmerce/skipMergeComponents.txt", e);
			} finally {
				try {
					bufferedReader.close();
				} catch (IOException ioe) {
					LOG.error("Error closing resource - /broadleaf-commmerce/skipMergeComponents.txt", ioe);
				}
			}
		}
	}

	/**
	* Examines the properties file for an entry with an id equal to the component that we want
	* to ignore and then removes all keys that have the same number (e.g. if xpath.28 is the key
	* then handler.28, xpath.28, and priority.28 will all be removed).
	*
	* @param props
	* @param componentName
	*/
	private void removeSkipMergeComponents(Properties props, String componentName) {
		String lookupName = "@id='" + componentName.trim() + "'";
		String key = findComponentKey(lookupName, props);
		while (key != null) {
			removeItemsMatchingKey(key, props);
			key = findComponentKey(lookupName, props);
		}
	}

	/**
	* Examines the properties file for an entry that contains the passed in component id string and returns its key
	*
	* to ignore.
	*
	* @param componentName
	* @param props
	* @return
	*/
	private String findComponentKey(String componentIdStr, Properties props) {
		for (Map.Entry<Object, Object> entry : props.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof String) {
				String valueStr = (String) value;
				if (valueStr.contains(componentIdStr)) {
					Object key = entry.getKey();
					if (key instanceof String) {
						return (String) key;
					}
				}
			}
		}
		return null;
	}

	/**
	* Removes all keys that share the same number. (e.g. if xpath.28 is the key
	* then handler.28, xpath.28, and priority.28 will all be removed).
	*
	* @param firstKey
	* @param props
	* @return
	*/
	private void removeItemsMatchingKey(String firstKey, Properties props) {
		int dotPos = firstKey.indexOf(".");
		if (dotPos > 0) {
			String keyNumberToMatch = firstKey.substring(dotPos);

			Iterator<Object> iter = props.keySet().iterator();

			while (iter.hasNext()) {
				Object keyObj = iter.next();
				if (keyObj instanceof String) {
					String keyStr = (String) keyObj;
					dotPos = keyStr.indexOf(".");
					String keyNumber = keyStr.substring(dotPos);
					if (keyNumber.equals(keyNumberToMatch)) {
						iter.remove();
					}
				}
			}
		}
	}

	/**
	* Merge 2 xml document streams together into a final resulting stream. During
	* the merge, various merge business rules are followed based on configuration
	* defined for various merge points.
	*
	* @param stream1
	* @param stream2
	* @return the stream representing the merged document
	* @throws org.broadleafcommerce.common.extensibility.context.merge.exceptions.MergeException
	*/
	public ResourceInputStream merge(ResourceInputStream stream1, ResourceInputStream stream2) throws MergeException {
		try {
			Document doc1 = builder.parse(stream1);
			Document doc2 = builder.parse(stream2);

			List<Node> exhaustedNodes = new ArrayList<Node>();

			// process any defined handlers
			for (MergeHandler handler : this.handlers) {
				if (LOG.isDebugEnabled()) {
					LOG.debug("Processing handler: " + handler.getXPath());
				}
				MergePoint point = new MergePoint(handler, doc1, doc2);
				List<Node> list = point.merge(exhaustedNodes);
				exhaustedNodes.addAll(list);
			}

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer xmlTransformer = tFactory.newTransformer();
			xmlTransformer.setOutputProperty(OutputKeys.VERSION, "1.0");
			xmlTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			xmlTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
			xmlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

			if (doc1.getDoctype() != null && doc1.getDoctype().getSystemId() != null) {
				xmlTransformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, doc1.getDoctype().getSystemId());
			}

			DOMSource source = new DOMSource(doc1);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
			StreamResult result = new StreamResult(writer);
			xmlTransformer.transform(source, result);

			byte[] itemArray = baos.toByteArray();

			return new ResourceInputStream(new ByteArrayInputStream(itemArray), stream2.getName(), stream1.getNames());
		} catch (Exception e) {
			throw new MergeException(e);
		}
	}

	private void setHandlers(Properties props) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		List<MergeHandler> tempHandlers = Lists.newArrayList();
		String[] keys = props.keySet().toArray(new String[props.keySet().size()]);
		for (String key : keys) {
			if (key.startsWith("handler.")) {
				MergeHandler temp = (MergeHandler) Class.forName(props.getProperty(key)).newInstance();
				String name = key.substring(8, key.length());
				temp.setName(name);

				String priority = props.getProperty("priority." + name);
				if (priority != null) {
					temp.setPriority(Integer.parseInt(priority));
				}

				String xpath = props.getProperty("xpath." + name);
				if (xpath != null) {
					temp.setXPath(xpath);
				}

				String matcherType = props.getProperty("matcherType." + name);
				if (matcherType != null) {
					temp.setMatcherType(MergeMatcherType.valueOf(matcherType));
				}

				tempHandlers.add(temp);
			}
		}

		List<MergeHandler> finalHandlers = Lists.newArrayList();
		for (MergeHandler temp : tempHandlers) {
			if (temp.getName().contains(".")) {
				contextualNameMatcher.setContext(temp.getName().substring(0, temp.getName().lastIndexOf(".")));

				MergeHandler parent = VIterables.getSingleResult(contextualNameMatcher.getMatches(tempHandlers));
				parent.getChildren().add(temp);
			} else {
				finalHandlers.add(temp);
			}
		}

		this.handlers = finalHandlers;
	}

	private Properties loadProperties() throws IOException {
		Properties props = new Properties();
		props.load(Configurations.resolveClasspathResource(this.defaultHandlerConfiguration, this.applicationContext).getInputStream());

		List<ResourceInputStream> configurations = Configurations.getConfigurations(this.patchLocations, this.applicationContext);
		for (ResourceInputStream configuration : configurations) {
			props.load(configuration);
		}

		return props;
	}

	public String serialize(InputStream in) {
		InputStreamReader reader = null;
		int temp;
		StringBuilder item = new StringBuilder();
		boolean eof = false;
		try {
			reader = new InputStreamReader(in);
			while (!eof) {
				temp = reader.read();
				if (temp == -1) {
					eof = true;
				} else {
					item.append((char) temp);
				}
			}
		} catch (IOException e) {
			LOG.error("Unable to merge source and patch locations", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Throwable e) {
					LOG.error("Unable to merge source and patch locations", e);
				}
			}
		}

		return item.toString();
	}

	@Override
	public List<ConfigurationLocation> getPatchLocations() {
		return patchLocations;
	}

	@Override
	public void setPatchLocations(List<ConfigurationLocation> patchLocations) {
		this.patchLocations = patchLocations;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}