package org.alloy.core.managed.application;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface Application {
	public List<String> getCommandLineArguments();
}