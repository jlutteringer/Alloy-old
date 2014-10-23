package org.alloy.metal.url;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.alloy.metal.spring.WithLogger;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class AlloyUrl extends WithLogger {
	private final List<String> pathStrings;
	private final Map<String, Object> requestParameters;

	public AlloyUrl(List<String> pathStrings, Map<String, Object> requestParameters) {
		this.pathStrings = Collections.unmodifiableList(pathStrings);
		this.requestParameters = Collections.unmodifiableMap(requestParameters);
	}

	public String getUrl() {
		String url = "";
		for (String pathString : pathStrings) {
			url = url + '/' + _Url.encode(pathString);
		}

		if (!requestParameters.isEmpty()) {
			url = url + '?';

			for (Entry<String, Object> parameter : requestParameters.entrySet()) {
				url = url + _Url.encode(parameter.getKey()) + "=" + _Url.encode(parameter.getValue().toString()) + "&";
			}
			url = StringUtils.removeEnd(url, "&");
		}

		logger.debug(url);
		return url;
	}

	public String getRedirectUrl() {
		return "redirect:" + this.getUrl();
	}

	public List<String> getPathStrings() {
		return pathStrings;
	}

	public Map<String, Object> getRequestParameters() {
		return requestParameters;
	}

	public static class AlloyUrlBuilder {
		private final List<String> pathStrings = Lists.newArrayList();
		private final Map<String, Object> requestParameters = Maps.newHashMap();

		public AlloyUrlBuilder parsePath(String url) {
			AlloyUrl parsedUrl = _Url.parse(url);
			pathStrings.addAll(parsedUrl.getPathStrings());
			requestParameters.putAll(parsedUrl.getRequestParameters());
			return this;
		}

		public AlloyUrlBuilder addPath(Object... objects) {
			return addPath(Arrays.asList(objects));
		}

		public <T> AlloyUrlBuilder addPath(Iterable<T> objects) {
			objects.forEach((object) -> pathStrings.add(object.toString()));
			return this;
		}

		public AlloyUrlBuilder addRequestParameter(String name, Object value) {
			if (value != null) {
				requestParameters.put(name, value.toString());
			}
			return this;
		}

		public AlloyUrl build() {
			return new AlloyUrl(pathStrings, requestParameters);
		}
	}
}