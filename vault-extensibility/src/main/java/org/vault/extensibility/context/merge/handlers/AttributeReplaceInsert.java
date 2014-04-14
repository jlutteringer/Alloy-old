package org.vault.extensibility.context.merge.handlers;

import org.vault.base.utilities.xpath.XPathUtils;
import org.w3c.dom.Node;

public class AttributeReplaceInsert extends BaseMergeHandler {
	@Override
	public void mergeInternal(Node source, Node patch) {
		for (Node patchAttribute : XPathUtils.getAttributeNodes(patch)) {
			XPathUtils.element(source).setAttributeNode(
					XPathUtils.attribute(
							XPathUtils.cloneAndImport(patchAttribute, source.getOwnerDocument())));
		}
	}
}
