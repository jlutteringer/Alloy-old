package org.vault.core.managed.bootstrap.handlers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.extensibility.bootstrap.domain.BootstrapHandler;
import org.vault.extensibility.bootstrap.domain.BootstrapHandlerContext;

@Component
public class CoreBootstrapHandlerContext implements BootstrapHandlerContext {

	@Autowired
	private List<BootstrapHandler> handlers;

	public void run() {
		for (BootstrapHandler handler : handlers) {
			handler.run();
		}
	}
}
