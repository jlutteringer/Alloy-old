package org.vault.bootstrap.managed.logging.initialization;

import java.util.Collection;

import org.alloy.metal.enumeration._ExtendableEnumeration;
import org.alloy.metal.spring.AlloyBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class EnumerationInitializer extends AlloyBean implements InitializingBean, ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		if (!_ExtendableEnumeration.isConfigured()) {
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

			_ExtendableEnumeration.endConfiguration();
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
