package org.vault.extensibility.context.merge.handlers;

import org.w3c.dom.Node;

public abstract class BaseMergeHandler extends AbstractMergeHandler {
	public void merge(Node source, Node patch) {
		mergeInternal(source, patch);
	}

	public abstract void mergeInternal(Node source, Node patch);
}