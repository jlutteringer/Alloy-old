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
package org.vault.site.managed.resource.service;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;

public interface ResourceBundlingService {
	public boolean hasBundleForResource(String path);

	public Resource getBundleForResource(String path, List<Resource> locations);

	public Map<String, Collection<Resource>> getBundles();

	public String registerBundle(String bundleName, List<String> files, VaultResourceHttpRequestHandler handler) throws IOException;
}