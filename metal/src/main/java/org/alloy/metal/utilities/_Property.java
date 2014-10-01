package org.alloy.metal.utilities;

import java.util.Properties;

import org.springframework.util.PropertyPlaceholderHelper;

public class _Property {
	public static String resolve(String template, Properties properties) {
		PropertyPlaceholderHelper h = new PropertyPlaceholderHelper("${", "}");
		return h.replacePlaceholders(template, properties);
	}
}