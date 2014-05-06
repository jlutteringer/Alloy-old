package org.vault.bootstrap.managed.configuration;

public class DirectoryStructure {
	private static final String LOGGING_DIRECTORY = "logging";
	private static final String LOGGING_FILE_STRUCTIRE = "log4j-%s.xml";

	public static String getLogFileStructure() {
		return LOGGING_DIRECTORY + "/" + LOGGING_FILE_STRUCTIRE;
	}
}
