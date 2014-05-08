package org.vault.bootstrap.managed.logging.merge;

import org.springframework.stereotype.Component;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.merge.AbstractMergeManager;

@Component
public class LoggingMergeManagerImpl extends AbstractMergeManager implements LoggingMergeManager {
	@Override
	protected String getMergeDescriptionLocation() {
		return VConfigurationFileConstants.LOG4J_MERGE_DESCRIPTION_LOCATION;
	}
}