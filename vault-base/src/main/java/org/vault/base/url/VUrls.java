package org.vault.base.url;

import org.apache.commons.lang3.StringUtils;

public class VUrls {
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
}
