package org.vault.bootstrap.managed.initialization.service;

import java.util.Collections;
import java.util.List;

import org.alloy.metal.order.Orderable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CorePreInitializationContext implements PreInitializationContext {

	@Autowired
	private List<PreInitializingBean> handlers;

	@Override
	public void initialize() {
		Collections.sort(handlers, Orderable.comparator());

		for (PreInitializingBean handler : handlers) {
			handler.initialize();
		}
	}
}
