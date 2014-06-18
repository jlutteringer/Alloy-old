package org.vault.base.utilities.constants;

public class VConfigurationFileConstants {
	public static final String BOOTSTRAP_LOG4J_RESOURCE = "log4j-bootstrap.xml";
	public static final String LOGGING_DIRECTORY = "logging";
	public static final String PROPERTIES_DIRECTORY = "properties";
	public static final String PERSISTENCE_DIRECTORY = "persistence";

	public static final String LOG4J_MERGE_DESCRIPTION_LOCATION = "merge/logging-merge.properties";
	public static final String CONTEXT_MERGE_DESCRIPTION_LOCATION = "merge/context-merge.properties";

	public static final String CONTEXT_RESOURCE_DIRECTORY = "configuration";

	public static final String BOOTSTRAP_SCAN_BASE_PACKAGE = "org.vault.bootstrap.managed";

	private static final String LOGGING_FILE_STRUCTIRE = "log4j-%s.xml";
	private static final String PROPERTIES_FILE_STRUCTIRE = "%s.properties";
	private static final String PERSISTENCE_FILE_STRUCTIRE = "persistence-%s.xml";

	public static String getLogFileStructure() {
		return LOGGING_DIRECTORY + "/" + LOGGING_FILE_STRUCTIRE;
	}

	public static String getPropertyFileStructure() {
		return PROPERTIES_DIRECTORY + "/" + PROPERTIES_FILE_STRUCTIRE;
	}

	public static String getPersistenceFileStructure() {
		return PERSISTENCE_DIRECTORY + "/" + PERSISTENCE_FILE_STRUCTIRE;
	}
}