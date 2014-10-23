package org.alloy.metal.url;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import org.alloy.metal.string._String;
import org.alloy.metal.url.AlloyUrl.AlloyUrlBuilder;
import org.alloy.metal.utilities._Exception;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class _Url {
	private static String VERSION_TAG = "${version}";

	public static String version(String url, String version) {
		if (url.contains(VERSION_TAG)) {
			return url.replace(VERSION_TAG, version);
		}

		String[] urlPieces = url.split("/");

		// we start at 1 because index 0 is empty string for urls that start with / ("normalized" urls)
		if (urlPieces.length >= 2) {
			url = "/" + urlPieces[1] + "/" + version;
			for (int count = 2; count < urlPieces.length; count++) {
				url = url + "/" + urlPieces[count];
			}
		}

		return url;
	}

	public static String unVersion(String url, String version) {
		return url.replace("/" + version, "");
	}

	public static String normalize(String url) {
		if (StringUtils.isBlank(url)) {
			return "/";
		}

		url = url.trim();
		if (!url.startsWith("/")) {
			url = "/" + url;
		}

		if (url.endsWith("/")) {
			url = StringUtils.removeEnd(url, "/");
		}

		return url;
	}

	public static boolean isVersioned(String url, String version) {
		return url.contains("/" + version);
	}

	public static String encode(String pathString) {
		return _Exception.propagate(() -> {
			String encoded = URLEncoder.encode(pathString, _String.CHARACTER_ENCODING);
			return encoded.replace("+", "%20");
		});
	}

	public static String decode(String pathString) {
		return _Exception.propagate(() -> URLDecoder.decode(pathString, _String.CHARACTER_ENCODING));
	}

	public static AlloyUrlBuilder create() {
		return new AlloyUrlBuilder();
	}

	// FUTURE
	/**
	 * right now this method is very simple and only handles urls with no query string! it will unencode tho
	 * @param url
	 * @return
	 */
	public static AlloyUrl parse(String url) {
		List<String> paths = Lists.newArrayList();
		for (String pathPart : url.split("/")) {
			if (!StringUtils.isEmpty(pathPart)) {
				paths.add(_Url.decode(pathPart));
			}
		}
		return new AlloyUrl(paths, Maps.<String, Object> newHashMap());
	}
}