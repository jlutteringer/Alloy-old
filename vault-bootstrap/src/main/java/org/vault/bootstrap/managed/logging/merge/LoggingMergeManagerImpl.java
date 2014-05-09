package org.vault.bootstrap.managed.logging.merge;

import org.springframework.stereotype.Component;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.merge.AbstractXmlMergeManager;

@Component
public class LoggingMergeManagerImpl extends AbstractXmlMergeManager implements LoggingMergeManager {
	@Override
	protected String getMergeDescriptionLocation() {
		return VConfigurationFileConstants.LOG4J_MERGE_DESCRIPTION_LOCATION;
	}
}