package org.vault.core.test;

import org.vault.base.collections.tree.Tree;
import org.vault.base.collections.tree.Trees;

public class Tester {
	public static void main(String[] args) throws Exception {
//		CoreApplicationBootstrapper bootstrap = new CoreApplicationBootstrapper();
//		bootstrap.setBootstrapConfigurationLocation(Lists.newArrayList("bootstrap/vault-bootstrap-applicationContext.xml"));
//
//		MergeApplicationContext context = bootstrap.bootstrap();
//
//		ConfigurationLocation test = Configurations.createClasspathLocation("bootstrap/vault-bootstrap-applicationContext.xml");
//		ConfigurationLocation test2 = Configurations.createClasspathLocation("bootstrap/log4j-bootstrap.xml");
//		ConfigurationLocation test3 = Configurations.createClasspathLocation("logging/log4j-dev.xml");
//		List<ResourceInputStream> testConfigurations = Configurations.getConfigurations(Lists.newArrayList(test, test2, test3), context);
//		testConfigurations.forEach((element) -> {
//			System.out.println("========================");
//			System.out.println(element);
//			System.out.println("========================");
//			try {
//				ByteStreams.copy(element, System.out);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println();
//			System.out.println("========================");
//		});
//
//		ConfigurationLocation config1 = Configurations.createEnvironmentLocation("log4j-%s.xml", EnvironmentType.DEV);
//
//		List<ResourceInputStream> configurations = Configurations.getConfigurations(Lists.newArrayList(config1), context);
//		configurations.forEach((element) -> System.out.println(element));

//		CoreApplicationBootstrapper bootstrap = new CoreApplicationBootstrapper();
//		bootstrap.setBootstrapConfigurationLocation(Lists.newArrayList("vault-test-bootstrap-applicationContext.xml"));
//		MergeApplicationContext context = bootstrap.bootstrap();
//		context.refresh();
//		context.start();

		Tree<String> tree = Trees.newHashTree("hello");
		Tree<String> child1 = tree.addChild("how");
		child1.addChild("I");
		child1.addChild("am");

		Tree<String> child2 = tree.addChild("are");
		child2.addChild("fine");

		tree.addChild("you");

		for (String asdf : Trees.iterateBreadthFirst(tree)) {
			System.out.println(asdf);
		}
	}
}