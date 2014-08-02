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

package org.alloy.metal.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import org.alloy.metal.collections.iterable._Iterable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

/**
* @author jfischer
*/
public class ResourceInputStream extends InputStream {

	private final InputStream is;
	private List<String> names = new ArrayList<String>(20);

	public ResourceInputStream(InputStream is, String name) {
		this.is = is;
		names.add(name);
	}

	public ResourceInputStream(InputStream is, String name, List<String> previousNames) {
		this.is = is;
		names.addAll(previousNames);
		if (!StringUtils.isEmpty(name)) {
			names.add(name);
		}
	}

	public List<String> getNames() {
		return names;
	}

	public String getName() {
		assert names.size() == 1;
		return names.get(0);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(100);
		int size = names.size();
		for (int j = 0; j < size; j++) {
			sb.append(names.get(j));
			if (j < size - 1) {
				sb.append(" : ");
			}
		}

		return sb.toString();
	}

	@Override
	public int available() throws IOException {
		return (is == null) ? -1 : is.available();
	}

	@Override
	public void close() throws IOException {
		is.close();
	}

	@Override
	public void mark(int i) {
		is.mark(i);
	}

	@Override
	public boolean markSupported() {
		return is.markSupported();
	}

	@Override
	public int read() throws IOException {
		return is.read();
	}

	@Override
	public int read(byte[] bytes) throws IOException {
		return is.read(bytes);
	}

	@Override
	public int read(byte[] bytes, int i, int i1) throws IOException {
		return is.read(bytes, i, i1);
	}

	@Override
	public void reset() throws IOException {
		is.reset();
	}

	@Override
	public long skip(long l) throws IOException {
		return is.skip(l);
	}

	public static Resource toResource(ResourceInputStream stream) {
		return _Iterable.getSingleResult(toResources(Collections.singletonList(stream)), true);
	}

	public static List<Resource> toResources(List<ResourceInputStream> streams) {
		List<Resource> resources = Lists.newArrayList();
		for (ResourceInputStream stream : streams) {
			resources.add(new InputStreamResource(stream));
		}
		return resources;
	}

	public static Function<Resource, ResourceInputStream> transformer() {
		return (resource) -> {
			try {
				return new ResourceInputStream(resource.getInputStream(), resource.getURL().getPath());
			} catch (IOException e) {
				throw Throwables.propagate(e);
			}
		};
	}
}