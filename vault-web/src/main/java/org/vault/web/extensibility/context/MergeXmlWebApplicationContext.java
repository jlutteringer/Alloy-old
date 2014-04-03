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

package org.vault.web.extensibility.context;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.vault.base.utilities.configuration.ConfigurationLocation;
import org.vault.extensibility.context.MergeApplicationContext;
import org.vault.extensibility.context.MergeApplicationContextBeanDefinitionLoader;

public class MergeXmlWebApplicationContext extends XmlWebApplicationContext implements MergeApplicationContext {
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