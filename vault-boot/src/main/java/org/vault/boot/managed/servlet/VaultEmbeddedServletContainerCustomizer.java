package org.vault.boot.managed.servlet;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;
import org.vault.base.spring.beans.VaultBean;
import org.vault.base.utilities.resources.VResources;
import org.vault.core.managed.resource.VaultClasspathResourceManager;
import org.vault.module.registry.boot.BootModule;

@Component
public class VaultEmbeddedServletContainerCustomizer extends VaultBean implements EmbeddedServletContainerCustomizer {
	@Value("${keystore.file}")
	private String keystoreFile;

	@Value("${keystore.pass}")
	private String keystorePass;

	@Value("${port.http}")
	private Long httpPort;

	@Value("${port.https}")
	private Long httpsPort;

	@Autowired
	private VaultClasspathResourceManager resourceManager;

	@Autowired
	private BootModule bootModule;

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;

		Connector connector = new Connector(Http11NioProtocol.class.getName());
		connector.setPort(httpsPort.intValue());
		connector.setSecure(true);
		connector.setScheme("https");

		Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
		proto.setSSLEnabled(true);
		proto.setKeystoreFile(VResources.getPath(resourceManager.getResourceFromModule(bootModule, keystoreFile)));
		proto.setKeystorePass(keystorePass);
		proto.setKeystoreType("PKCS12");
		proto.setKeyAlias("tomcat");

		tomcat.addAdditionalTomcatConnectors(connector);

		tomcat.addConnectorCustomizers((defaultConnector) -> defaultConnector.setPort(httpPort.intValue()));
	}
}
