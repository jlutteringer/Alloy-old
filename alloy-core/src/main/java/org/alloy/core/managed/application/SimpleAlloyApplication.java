package org.alloy.core.managed.application;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class SimpleAlloyApplication implements Application {
	private static List<String> commandLineArguments;

	@Override
	public List<String> getCommandLineArguments() {
		return commandLineArguments;
	}

	public static void setCommandLineArguments(List<String> commandLineArguments) {
		SimpleAlloyApplication.commandLineArguments = Collections.unmodifiableList(commandLineArguments);
	}
}