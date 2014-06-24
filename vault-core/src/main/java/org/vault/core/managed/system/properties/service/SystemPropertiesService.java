package org.vault.core.managed.system.properties.service;

import org.vault.core.managed.system.properties.domain.SystemPropertyFieldType;

public interface SystemPropertiesService {
	/**
	* Preferred method for looking up properties. The method will return the configured value or
	* if no override value is found, it will return the value passed in to the method as the default value.
	*
	* @param name
	* @return
	*/
	public String resolveSystemProperty(String name);

	public String resolveSystemProperty(String name, String defaultValue);

	/**
	* Resolves an int system property. Returns 0 when no matching property
	* is found.
	*
	* @param name
	* @return
	*/
	public int resolveIntSystemProperty(String name);

	public int resolveIntSystemProperty(String name, int defaultValue);

	/**
	* Resolves a boolean system property. Returns false when no matching
	* system property is found.
	*
	* @param name
	* @return
	*/
	public boolean resolveBooleanSystemProperty(String name);

	/**
	*
	*/
	public boolean resolveBooleanSystemProperty(String name, boolean defaultValue);

	/**
	* Resolves an long system property. Returns 0 when no matching property
	* is found.
	* @param name
	* @return
	*/
	public long resolveLongSystemProperty(String name);

	public long resolveLongSystemProperty(String name, long defaultValue);

	/**
	* Determines if the given value is valid for the specified type
	*
	* @param sp
	* @return whether or not the SystemProperty is in a valid state
	*/
	public boolean isValueValidForType(String value, SystemPropertyFieldType type);
}
