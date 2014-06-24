package org.vault.core.managed.system.properties.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vault.core.managed.system.properties.domain.SystemPropertyFieldType;

@Service("systemPropertiesService")
public class SimpleSystemPropertiesService implements SystemPropertiesService {
	@Autowired
	protected RuntimeEnvironmentPropertiesManager propertiesManager;

	@Override
	public String resolveSystemProperty(String name, String defaultValue) {
		String result = resolveSystemProperty(name);
		if (result == null) {
			return defaultValue;
		}
		return result;
	}

	@Override
	public String resolveSystemProperty(String name) {
		return propertiesManager.getProperty(name);
	}

	@Override
	public int resolveIntSystemProperty(String name) {
		String systemProperty = resolveSystemProperty(name, "0");
		return Integer.valueOf(systemProperty).intValue();
	}

	@Override
	public int resolveIntSystemProperty(String name, int defaultValue) {
		String systemProperty = resolveSystemProperty(name, Integer.toString(0));
		return Integer.valueOf(systemProperty).intValue();
	}

	@Override
	public boolean resolveBooleanSystemProperty(String name) {
		String systemProperty = resolveSystemProperty(name, "false");
		return Boolean.valueOf(systemProperty).booleanValue();
	}

	@Override
	public boolean resolveBooleanSystemProperty(String name, boolean defaultValue) {
		String systemProperty = resolveSystemProperty(name, Boolean.toString(defaultValue));
		return Boolean.valueOf(systemProperty).booleanValue();
	}

	@Override
	public long resolveLongSystemProperty(String name) {
		String systemProperty = resolveSystemProperty(name, "0");
		return Long.valueOf(systemProperty).longValue();
	}

	@Override
	public long resolveLongSystemProperty(String name, long defaultValue) {
		String systemProperty = resolveSystemProperty(name, Long.toString(defaultValue));
		return Long.valueOf(systemProperty).longValue();
	}

	@Override
	public boolean isValueValidForType(String value, SystemPropertyFieldType type) {
		if (type.equals(SystemPropertyFieldType.BOOLEAN_TYPE)) {
			value = value.toUpperCase();
			if (value != null && (value.equals("TRUE") || value.equals("FALSE"))) {
				return true;
			}
		} else if (type.equals(SystemPropertyFieldType.INT_TYPE)) {
			try {
				Integer.parseInt(value);
				return true;
			} catch (Exception e) {
				// Do nothing - we will fail on validation
			}
		} else if (type.equals(SystemPropertyFieldType.LONG_TYPE)) {
			try {
				Long.parseLong(value);
				return true;
			} catch (Exception e) {
				// Do nothing - we will fail on validation
			}
		} else if (type.equals(SystemPropertyFieldType.DOUBLE_TYPE)) {
			try {
				Double.parseDouble(value);
				return true;
			} catch (Exception e) {
				// Do nothing - we will fail on validation
			}
		} else if (type.equals(SystemPropertyFieldType.STRING_TYPE)) {
			return true;
		}

		return false;
	}
}
