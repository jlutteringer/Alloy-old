package org.vault.bootstrap.managed.logging.initialization;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.vault.base.domain.phase.Phase;
import org.vault.base.spring.beans.initialization.AbstractPhasedInitializingBean;
import org.vault.base.utilities.logging.Logging;
import org.vault.bootstrap.managed.initialization.service.PreInitializingBean;
import org.vault.bootstrap.managed.logging.configuration.LoggingConfigurationManager;
import org.vault.bootstrap.managed.logging.merge.LoggingMergeManager;

import com.google.common.base.Throwables;

@Component
public class LoggingInitializer extends AbstractPhasedInitializingBean implements PreInitializingBean {
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

	@Override
	public Phase getPhase() {
		return Phase.BEGIN;
	}
}