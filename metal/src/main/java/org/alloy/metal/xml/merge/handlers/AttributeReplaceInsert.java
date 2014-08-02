package org.alloy.metal.xml.merge.handlers;

import org.alloy.metal.xml._XPath;
import org.w3c.dom.Node;

public class AttributeReplaceInsert extends BaseMergeHandler {
	@Override
	public void mergeInternal(Node source, Node patch) {
		for (Node patchAttribute : _XPath.getAttributeNodes(patch)) {
			_XPath.element(source).setAttributeNode(
					_XPath.attribute(
							_XPath.cloneAndImport(patchAttribute, source.getOwnerDocument())));
		}
	}
}
