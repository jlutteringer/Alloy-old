package org.alloy.forge.managed.initialization;

import java.io.IOException;

import org.alloy.forge.managed.configuration.LoggingConfigurationManager;
import org.alloy.forge.managed.merge.LoggingMergeManager;
import org.alloy.metal.logging._Logging;
import org.alloy.metal.order.Phase;
import org.alloy.metal.spring.initialization.AbstractPhasedInitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

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
			_Logging.configureLog4j(mergedResource.getInputStream());
		} catch (IOException e) {
			Throwables.propagate(e);
		}
	}

	@Override
	public Phase getOrderingObject() {
		return Phase.INITIALIZATION;
	}
}