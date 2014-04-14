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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.extensibility.context.merge.exceptions.MergeException;
import org.vault.extensibility.context.merge.exceptions.MergeManagerSetupException;

import com.google.common.collect.Iterables;

/**
*
* @author jfischer
*
*/
public class MergeXmlConfigResource {

	private static final Log LOG = LogFactory.getLog(MergeXmlConfigResource.class);

	private ApplicationContext applicationContext;
	private String defaultHandlerConfiguration;

	public MergeXmlConfigResource(String defaultHandlerConfiguration, ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.defaultHandlerConfiguration = defaultHandlerConfiguration;
	}

	public Resource getMergedConfigResource(List<ResourceInputStream> sources) throws BeansException {
		Resource configResource = null;
		ResourceInputStream merged = null;
		try {
			merged = merge(sources);

			// read the final stream into a byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			boolean eof = false;
			while (!eof) {
				int temp = merged.read();
				if (temp == -1) {
					eof = true;
				} else {
					baos.write(temp);
				}
			}
			configResource = new ByteArrayResource(baos.toByteArray());

			if (LOG.isDebugEnabled()) {
				LOG.debug("Merged config: \n" + serialize(configResource));
			}
		} catch (MergeException e) {
			throw new FatalBeanException("Unable to merge source and patch locations", e);
		} catch (MergeManagerSetupException e) {
			throw new FatalBeanException("Unable to merge source and patch locations", e);
		} catch (IOException e) {
			throw new FatalBeanException("Unable to merge source and patch locations", e);
		} finally {
			if (merged != null) {
				try {
					merged.close();
				} catch (Throwable e) {
					LOG.error("Unable to merge source and patch locations", e);
				}
			}
		}

		return configResource;
	}

	protected ResourceInputStream merge(List<ResourceInputStream> sources) throws MergeException, MergeManagerSetupException {
		if (sources.size() == 1) {
			return Iterables.getFirst(sources, null);
		}

		ResourceInputStream response = null;
		ResourceInputStream[] pair = new ResourceInputStream[2];
		pair[0] = sources.get(0);
		for (int j = 1; j < sources.size(); j++) {
			pair[1] = sources.get(j);
			response = mergeItems(pair[0], pair[1]);
			try {
				pair[0].close();
			} catch (Throwable e) {
				LOG.error("Unable to merge source and patch locations", e);
			}
			try {
				pair[1].close();
			} catch (Throwable e) {
				LOG.error("Unable to merge source and patch locations", e);
			}
			pair[0] = response;
		}

		return response;
	}

	protected ResourceInputStream mergeItems(ResourceInputStream sourceLocationFirst, ResourceInputStream sourceLocationSecond) throws MergeException, MergeManagerSetupException {
		ResourceInputStream response = new MergeManager(defaultHandlerConfiguration, applicationContext).merge(sourceLocationFirst, sourceLocationSecond);

		return response;
	}

	public String serialize(Resource resource) {
		String response = "";
		try {
			response = serialize(resource.getInputStream());
		} catch (IOException e) {
			LOG.error("Unable to merge source and patch locations", e);
		}

		return response;
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

	protected byte[] buildArrayFromStream(InputStream source) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		boolean eof = false;
		try {
			while (!eof) {
				int temp = source.read();
				if (temp == -1) {
					eof = true;
				} else {
					baos.write(temp);
				}
			}
		} finally {
			try {
				source.close();
			} catch (Throwable e) {
				LOG.error("Unable to merge source and patch locations", e);
			}
		}

		return baos.toByteArray();
	}
}
