package org.vault.base.utilities.xpath;

import java.util.List;

import org.vault.base.utilities.matcher.AbstractMatcher;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.google.common.collect.Lists;

public class XPathUtils {
	public static abstract class NodeMatcher extends AbstractMatcher<Node> {
		// No op
	}

	public static Node cloneAndImport(Node node, Document document) {
		return document.importNode(node.cloneNode(true), true);
	}

	public static List<Node> getAttributeNodes(Node node) {
		List<Node> nodes = Lists.newArrayList();

		NamedNodeMap attributes = node.getAttributes();
		for (int j = 0; j < attributes.getLength(); j++) {
			nodes.add(attributes.item(j));
		}

		return nodes;
	}

	public static NodeMatcher getNameNodeMatcher(final Node match) {
		return new NodeMatcher() {
			@Override
			public boolean matches(Node input) {
				if (match.getNodeName().equals(input.getNodeName())) {
					return true;
				}
				return false;
			}
		};
	}

	public static Element element(Node node) {
		return (Element) node;
	}

	public static Attr attribute(Node node) {
		return (Attr) node;
	}

	public static boolean hasAttribute(Node node, String attribute) {
		if (node.getAttributes().getNamedItem(attribute) == null) {
			return false;
		}
		return true;
	}

	public static String getAttributeValue(Node node, String attribute) {
		if (XPathUtils.hasAttribute(node, attribute)) {
			return node.getAttributes().getNamedItem(attribute).getNodeValue();
		}
		return null;
	}

	public static boolean attributeEqual(Node first, Node second, String attribute) {
		if (!XPathUtils.hasAttribute(first, attribute) || !XPathUtils.hasAttribute(second, attribute)) {
			return false;
		}
		return XPathUtils.getAttributeValue(first, attribute).equals(XPathUtils.getAttributeValue(second, attribute));
	}

	public static Document getDocument(Node node) {
		if (node instanceof Document) {
			return (Document) node;
		}
		return node.getOwnerDocument();
	}
}
