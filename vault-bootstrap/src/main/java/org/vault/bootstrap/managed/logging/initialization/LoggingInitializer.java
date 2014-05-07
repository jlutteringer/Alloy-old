package org.vault.bootstrap.managed.logging.initialization;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.spring.beans.AbstractInitializingBean;
import org.vault.base.utilities.logging.Logging;
import org.vault.bootstrap.managed.configuration.logging.LoggingConfigurationManager;
import org.vault.bootstrap.managed.initialization.service.PreInitializingBean;
import org.vault.bootstrap.managed.logging.merge.LoggingMergeManager;

import com.google.common.base.Throwables;

@Component
public class LoggingInitializer extends AbstractInitializingBean implements PreInitializingBean {
	@Autowired
	private LoggingConfigurationManager configurationManager;

	@Autowired
	private LoggingMergeManager mergeManager;

	@Override
	public void initialize() {
		Resource mergedResource = mergeManager.getMergedResource(configurationManager.buildConfigurationLocations());

		try {
			Logging.configureLog4j(mergedResource.getInputStream());
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}
}