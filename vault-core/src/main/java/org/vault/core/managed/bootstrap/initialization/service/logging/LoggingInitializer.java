package org.vault.core.managed.bootstrap.initialization.service.logging;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.vault.base.spring.beans.AbstractInitializingBean;
import org.vault.core.managed.bootstrap.configuration.logging.LoggingConfigurationManager;
import org.vault.core.managed.bootstrap.initialization.service.PreInitializingBean;
import org.vault.extensibility.logging.MergeLog4jConfiguration;

import com.google.common.base.Throwables;

@Component
public class LoggingInitializer extends AbstractInitializingBean implements PreInitializingBean, ApplicationContextAware {
	@Autowired
	private LoggingConfigurationManager configurationManager;

	private ApplicationContext bootstrapApplicationContext;

	@Override
	public void initialize() {
		MergeLog4jConfiguration configuration = new MergeLog4jConfiguration();
		configuration.setApplicationContext(bootstrapApplicationContext);
		configuration.setPatchLocations(configurationManager.buildConfigurationLocations());

		try {
			configuration.loadMergedLog4jConfiguration();
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext bootstrapApplicationContext) throws BeansException {
		this.bootstrapApplicationContext = bootstrapApplicationContext;
	}
}