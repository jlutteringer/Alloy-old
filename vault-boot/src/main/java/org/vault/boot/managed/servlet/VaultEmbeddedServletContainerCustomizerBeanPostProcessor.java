package org.vault.boot.managed.servlet;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizerBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class VaultEmbeddedServletContainerCustomizerBeanPostProcessor extends EmbeddedServletContainerCustomizerBeanPostProcessor {

}
