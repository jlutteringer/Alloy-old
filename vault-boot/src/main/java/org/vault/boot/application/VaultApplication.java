package org.vault.boot.application;

import java.util.List;

import org.vault.bootstrap.VaultApplicationBootstrapper;
import org.vault.bootstrap.context.VaultApplicationContext;

import com.google.common.collect.Lists;

public class VaultApplication {

	private List<String> bootstrapApplicationContexts = Lists.newArrayList("vault-bootstrap-applicationContext.xml");

	public VaultApplication(String[] args) {

	}

	public void run() {
		VaultApplicationBootstrapper bootstrap = new VaultApplicationBootstrapper(VaultEmbeddedWebApplicationContext::new);
		bootstrap.setBootstrapConfigurationLocations(bootstrapApplicationContexts);
		VaultApplicationContext context = bootstrap.bootstrap();
		bootstrap.stop();

		context.refresh();
		context.start();
	}

	public static void run(String[] args) {
		new VaultApplication(args).run();
	}
}
