package org.alloy.core.application;

import java.util.List;

import org.alloy.core.managed.application.SimpleAlloyApplication;
import org.alloy.forge.bootstrap.AlloyBootstrapper;
import org.alloy.forge.context.AlloyApplicationContext;
import org.alloy.forge.context.AlloyXmlApplicationContext;
import org.alloy.metal.collections.lists._Lists;

import com.google.common.collect.Lists;

public class AlloyApplicationRunner {
	private List<String> bootstrapApplicationContexts = Lists.newArrayList("forge-applicationContext.xml");

	public AlloyApplicationRunner(String[] args) {
		SimpleAlloyApplication.setCommandLineArguments(_Lists.list(args));
	}

	public void run() {
		AlloyBootstrapper bootstrap = new AlloyBootstrapper(AlloyXmlApplicationContext::new);
		bootstrap.setBootstrapConfigurationLocations(bootstrapApplicationContexts);
		AlloyApplicationContext context = bootstrap.bootstrap();
		bootstrap.stop();

		context.refresh();
		context.start();
	}

	public static void run(String[] args) {
		new AlloyApplicationRunner(args).run();
	}
}