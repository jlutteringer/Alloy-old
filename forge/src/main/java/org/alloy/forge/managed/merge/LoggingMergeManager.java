package org.alloy.forge.managed.merge;

import org.alloy.forge.merge.AbstractXmlMergeManager;
import org.alloy.forge.merge.MergeManager;
import org.springframework.stereotype.Component;
import org.vault.base.utilities.constants.AlloyConfigurationConstants;

public interface LoggingMergeManager extends MergeManager {
	@Component
	public static class LoggingMergeManagerImpl extends AbstractXmlMergeManager implements LoggingMergeManager {
		@Override
		protected String getMergeDescriptionLocation() {
			return AlloyConfigurationConstants.LOG4J_MERGE_DESCRIPTION_LOCATION;
		}
	}
}