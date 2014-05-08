package org.vault.bootstrap.managed.context.merge;

import org.springframework.stereotype.Service;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.bootstrap.managed.merge.AbstractMergeManager;

@Service
public class ContextMergeManagerImpl extends AbstractMergeManager implements ContextMergeManager {
	@Override
	protected String getMergeDescriptionLocation() {
		return VConfigurationFileConstants.CONTEXT_MERGE_DESCRIPTION_LOCATION;
	}
}
