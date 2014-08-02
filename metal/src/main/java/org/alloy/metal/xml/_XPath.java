package org.alloy.metal.xml;

import java.util.List;
import java.util.function.Predicate;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import com.google.common.collect.Lists;

public class _XPath {
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

	public static Predicate<Node> filterByName(String name) {
		return (node) -> name.equals(node.getNodeName());
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
		if (_XPath.hasAttribute(node, attribute)) {
			return node.getAttributes().getNamedItem(attribute).getNodeValue();
		}
		return null;
	}

	public static boolean attributeEqual(Node first, Node second, String attribute) {
		if (!_XPath.hasAttribute(first, attribute) || !_XPath.hasAttribute(second, attribute)) {
			return false;
		}
		return _XPath.getAttributeValue(first, attribute).equals(_XPath.getAttributeValue(second, attribute));
	}

	public static Document getDocument(Node node) {
		if (node instanceof Document) {
			return (Document) node;
		}
		return node.getOwnerDocument();
	}
}
