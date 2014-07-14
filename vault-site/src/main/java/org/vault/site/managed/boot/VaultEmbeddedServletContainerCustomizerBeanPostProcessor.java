package org.vault.site.managed.boot;

import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizerBeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class VaultEmbeddedServletContainerCustomizerBeanPostProcessor extends EmbeddedServletContainerCustomizerBeanPostProcessor {

}
