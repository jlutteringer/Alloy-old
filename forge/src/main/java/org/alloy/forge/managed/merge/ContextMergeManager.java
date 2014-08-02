package org.alloy.forge.managed.merge;

import org.alloy.forge.merge.AbstractXmlMergeManager;
import org.alloy.forge.merge.MergeManager;
import org.alloy.metal.configuration.AlloyConfigurationConstants;
import org.springframework.stereotype.Component;

public interface ContextMergeManager extends MergeManager {
	@Component
	public static class ContextMergeManagerImpl extends AbstractXmlMergeManager implements ContextMergeManager {
		@Override
		protected String getMergeDescriptionLocation() {
			return AlloyConfigurationConstants.CONTEXT_MERGE_DESCRIPTION_LOCATION;
		}
	}
}