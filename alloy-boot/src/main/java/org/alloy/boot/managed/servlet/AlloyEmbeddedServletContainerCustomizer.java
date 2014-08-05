package org.alloy.boot.managed.servlet;

import org.alloy.core.managed.resource.AlloyResourceManager;
import org.alloy.metal.resource._Resource;
import org.alloy.metal.spring.AlloyBean;
import org.alloy.module.registry.boot.BootModule;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizerBeanPostProcessor;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.stereotype.Component;

@Component
public class AlloyEmbeddedServletContainerCustomizer extends AlloyBean implements EmbeddedServletContainerCustomizer {
	@Value("${keystore.file}")
	private String keystoreFile;

	@Value("${keystore.pass}")
	private String keystorePass;

	@Value("${port.http}")
	private Long httpPort;

	@Value("${port.https}")
	private Long httpsPort;

	@Value("${https.enable}")
	private boolean enableHttps;

	@Autowired
	private AlloyResourceManager resourceManager;

	@Autowired
	private BootModule bootModule;

	@Override
	public void customize(ConfigurableEmbeddedServletContainer container) {
		TomcatEmbeddedServletContainerFactory tomcat = (TomcatEmbeddedServletContainerFactory) container;

		if (enableHttps) {
			logger.debug("Enabling https connector for embedded tomcat servlet");
			Connector connector = new Connector(Http11NioProtocol.class.getName());
			connector.setPort(httpsPort.intValue());
			connector.setSecure(true);
			connector.setScheme("https");

			Http11NioProtocol proto = (Http11NioProtocol) connector.getProtocolHandler();
			proto.setSSLEnabled(true);
			proto.setKeystoreFile(_Resource.getPath(resourceManager.getResourceFromModule(bootModule, keystoreFile)));
			proto.setKeystorePass(keystorePass);
			proto.setKeystoreType("PKCS12");
			proto.setKeyAlias("tomcat");

			tomcat.addAdditionalTomcatConnectors(connector);
		}

		tomcat.addConnectorCustomizers((defaultConnector) -> defaultConnector.setPort(httpPort.intValue()));
	}

	@Component
	public static class AlloyEmbeddedServletContainerCustomizerBeanPostProcessor extends EmbeddedServletContainerCustomizerBeanPostProcessor {
		// This class registers EmbeddedServletContainerCustomizer beans to the spring boot context
	}
}
