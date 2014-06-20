package org.vault.bootstrap.managed.logging.initialization;

import java.util.Collection;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.vault.base.enumeration.VEnumerations;
import org.vault.base.spring.beans.AbstractVaultBean;

@Component
public class EnumerationInitializer extends AbstractVaultBean implements InitializingBean, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!VEnumerations.isConfigured()) {
			Collection<EnumerationLoader> loaders = applicationContext.getBeansOfType(EnumerationLoader.class).values();

			loaders.forEach((element) -> {
				element.getEnumerationsToLoad().forEach((enumeration) -> {
					try {
						logger.debug("Loading enumeration: " + enumeration.getName());
						Class.forName(enumeration.getName());
					} catch (Exception e) {
						logger.error("Couldn't load specified enumeration", e);
					}
				});
			});

			VEnumerations.endConfiguration();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
