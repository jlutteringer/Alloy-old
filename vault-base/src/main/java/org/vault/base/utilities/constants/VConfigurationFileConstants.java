package org.vault.base.utilities.constants;

public class VConfigurationFileConstants {
	public static final String BOOTSTRAP_LOG4J_RESOURCE = "log4j-bootstrap.xml";
	public static final String LOGGING_DIRECTORY = "logging";

	public static final String LOG4J_MERGE_DESCRIPTION_LOCATION = "merge/logging/logging-merge.properties";

	private static final String LOGGING_FILE_STRUCTIRE = "log4j-%s.xml";

	public static String getLogFileStructure() {
		return LOGGING_DIRECTORY + "/" + LOGGING_FILE_STRUCTIRE;
	}
}
