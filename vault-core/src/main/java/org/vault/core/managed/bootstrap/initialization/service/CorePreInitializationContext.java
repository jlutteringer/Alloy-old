package org.vault.core.managed.bootstrap.initialization.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorePreInitializationContext implements PreInitializationContext {

	@Autowired
	private List<PreInitializingBean> handlers;

	@Override
	public void initialize() {
		for (PreInitializingBean handler : handlers) {
			handler.initialize();
		}
	}
}
