package org.vault.core.test;

import org.vault.bootstrap.CoreApplicationBootstrapper;
import org.vault.extensibility.context.MergeApplicationContext;

import com.google.common.collect.Lists;

public class Tester {
	public static void main(String[] args) throws Exception {
		CoreApplicationBootstrapper bootstrap = new CoreApplicationBootstrapper();
		bootstrap.setBootstrapConfigurationLocation(Lists.newArrayList("vault-test-bootstrap-applicationContext.xml"));
		MergeApplicationContext context = bootstrap.bootstrap();
		context.refresh();
		context.start();

	}
}