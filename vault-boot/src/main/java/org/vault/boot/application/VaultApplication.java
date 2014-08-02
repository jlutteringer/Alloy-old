package org.vault.boot.application;

import java.util.List;

import org.alloy.bootstrap.AlloyBootstrapper;
import org.alloy.bootstrap.context.AlloyApplicationContext;

import com.google.common.collect.Lists;

public class VaultApplication {

	private List<String> bootstrapApplicationContexts = Lists.newArrayList("vault-bootstrap-applicationContext.xml");

	public VaultApplication(String[] args) {

	}

	public void run() {
		AlloyBootstrapper bootstrap = new AlloyBootstrapper(VaultEmbeddedWebApplicationContext::new);
		bootstrap.setBootstrapConfigurationLocations(bootstrapApplicationContexts);
		AlloyApplicationContext context = bootstrap.bootstrap();
		bootstrap.stop();

		context.refresh();
		context.start();
	}

	public static void run(String[] args) {
		new VaultApplication(args).run();
	}
}
