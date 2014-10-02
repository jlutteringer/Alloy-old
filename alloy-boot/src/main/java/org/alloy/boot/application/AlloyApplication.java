package org.alloy.boot.application;

import java.util.List;

import org.alloy.forge.bootstrap.AlloyBootstrapper;
import org.alloy.forge.context.AlloyApplicationContext;

import com.google.common.collect.Lists;

public class AlloyApplication {

	private List<String> bootstrapApplicationContexts = Lists.newArrayList("forge-applicationContext.xml");

	public AlloyApplication(String[] args) {

	}

	public void run() {
		AlloyBootstrapper bootstrap = new AlloyBootstrapper(AlloyEmbeddedWebApplicationContext::new);
		bootstrap.setBootstrapConfigurationLocations(bootstrapApplicationContexts);
		AlloyApplicationContext context = bootstrap.bootstrap();
		bootstrap.stop();

		context.refresh();
		context.start();
	}

	public static void run(String[] args) {
		new AlloyApplication(args).run();
	}
}