package org.vault.bootstrap.managed.context.merge;

import org.springframework.stereotype.Service;
import org.vault.base.utilities.constants.AlloyConfigurationConstants;
import org.vault.bootstrap.managed.merge.AbstractXmlMergeManager;

@Service
public class ContextMergeManagerImpl extends AbstractXmlMergeManager implements ContextMergeManager {
	@Override
	protected String getMergeDescriptionLocation() {
		return AlloyConfigurationConstants.CONTEXT_MERGE_DESCRIPTION_LOCATION;
	}
}
