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

package org.vault.extensibility.context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.vault.base.resources.stream.ResourceInputStream;
import org.vault.extensibility.context.merge.MergeXmlConfigResource;
import org.vault.extensibility.context.merge.exceptions.MergeException;
import org.vault.extensibility.context.merge.exceptions.MergeManagerSetupException;

/**
*
* @author jfischer
*
*/
public class MergeApplicationContextXmlConfigResource extends MergeXmlConfigResource {
	private static final Log LOG = LogFactory.getLog(MergeApplicationContextXmlConfigResource.class);

	public MergeApplicationContextXmlConfigResource(String defaultHandlerConfiguration, ApplicationContext applicationContext) {
		super(defaultHandlerConfiguration, applicationContext);
	}

	/**
	* Generate a merged configuration resource, loading the definitions from the given streams. Note,
	* all sourceLocation streams will be merged using standard Spring configuration override rules. However, the patch
	* streams are fully merged into the result of the sourceLocations simple merge. Patch merges are first executed according
	* to beans with the same id. Subsequent merges within a bean are executed against tagnames - ignoring any
	* further id attributes.
	*
	* @param sources array of input streams for the source application context files
	* @param patches array of input streams for the patch application context files
	* @throws BeansException
	*/
	public Resource getConfigResources(List<ResourceInputStream> sources) throws BeansException {
		Resource mergedResource;

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
			mergedResource = new ByteArrayResource(baos.toByteArray());

			if (LOG.isDebugEnabled()) {
				LOG.debug("Merged ApplicationContext Including Patches: \n" + serialize(mergedResource));
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

		return mergedResource;
	}
}