/*
* #%L
* BroadleafCommerce Common Libraries
* %%
* Copyright (C) 2009 - 2013 Broadleaf Commerce
* %%
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
* #L%
*/
package org.vault.extensibility.context.merge;

import java.util.List;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vault.base.collections.iterable._Iterable;
import org.vault.base.utilities.matcher.Selector;
import org.vault.base.utilities.tuple.Tuple.Pair;
import org.vault.base.utilities.tuple.Tuples;
import org.vault.base.utilities.xpath.XPathUtils;
import org.vault.base.utilities.xpath.XPathUtils.NodeMatcher;
import org.vault.extensibility.context.merge.handlers.AttributeReplaceInsert;
import org.vault.extensibility.context.merge.handlers.MergeHandler;
import org.vault.extensibility.context.merge.handlers.MergeMatcherType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
* This class provides the xml merging apparatus at a defined XPath merge point in
* 2 xml documents. The MergeHandler that embodies the XPath point can have
* embedded XPath merge points, resulting in a cumulative effect with varying
* merge behavior for a sector of the documents. For example, it may be desirable
* to replace all the child nodes of a given node with all the child nodes from the same
* parent node in the patch document, with the exception of a single node. That single
* node may instead contribute its contents in a additive fashion (rather than replace).
*
* @author jfischer
*
*/
public class MergePoint {
	private static final Logger log = LogManager.getLogger(MergePoint.class);

	private MergeHandler handler;
	private Document sourceDoc;
	private Document patchDoc;
	private XPath xPath;

	public MergePoint(MergeHandler handler, Document sourceDoc, Document patchDoc) {
		this.handler = handler;
		this.sourceDoc = sourceDoc;
		this.patchDoc = patchDoc;
		XPathFactory factory = XPathFactory.newInstance();
		xPath = factory.newXPath();
	}

	/**
	* Execute the merge operation and also provide a list of nodes that have already been
	* merged. It is up to the handler implementation to respect or ignore this list.
	*
	* @param exhaustedNodes
	* @return list of merged nodes
	* @throws XPathExpressionException
	*/
	public List<Node> merge(List<Node> exhaustedNodes) throws XPathExpressionException, TransformerException {
		return merge(handler, exhaustedNodes, null, null);
	}

	private List<Node> merge(MergeHandler handler, List<Node> exhaustedNodes, Object sourceLoc, Object patchLoc) throws XPathExpressionException, TransformerException {
		if (sourceLoc == null) {
			sourceLoc = this.sourceDoc;
		}

		if (patchLoc == null) {
			patchLoc = this.patchDoc;
		}

		String[] xPaths = handler.getXPath().split(" ");

		List<Node> sourceNodes = Lists.newArrayList();
		List<Node> patchNodes = Lists.newArrayList();

		for (String xPathVal : xPaths) {
			sourceNodes.addAll(this.getNodes(xPathVal, sourceLoc));
			patchNodes.addAll(this.getNodes(xPathVal, patchLoc));
		}

		List<Pair<Node, Node>> matchedNodes = Lists.newArrayList();
		List<Node> unmatchedPatchNodes = Lists.newArrayList();

		for (Node sourceNode : sourceNodes) {
			Selector<Node> selector = this.getSelector(sourceNode, handler.getMatcherType());
			Node matchingNode = _Iterable.getSingleResult(selector.getMatches(patchNodes), true);
			if (matchingNode != null) {
				log.debug("Match found: " + matchingNode + " for source node " + sourceNode);
				matchedNodes.add(Tuples.of(sourceNode, matchingNode));
			}
		}

		for (Node patchNode : patchNodes) {
			boolean matchFound = false;
			for (Pair<Node, Node> matchedNode : matchedNodes) {
				if (patchNode == matchedNode.getSecond()) {
					matchFound = true;
					break;
				}
			}

			if (!matchFound) {
				unmatchedPatchNodes.add(patchNode);
			}
		}

		for (Pair<Node, Node> matchedNode : matchedNodes) {
			if (handler.getChildren().isEmpty()) {
				this.merge(this.getDefaultMergeHandler(), exhaustedNodes, matchedNode.getFirst(), matchedNode.getSecond());
			}
			else {
				for (MergeHandler childHandler : handler.getChildren()) {
					this.merge(childHandler, exhaustedNodes, matchedNode.getFirst(), matchedNode.getSecond());
				}
			}

			exhaustedNodes.add(matchedNode.getSecond());
			handler.merge(matchedNode.getFirst(), matchedNode.getSecond());
		}

		if (!sourceNodes.isEmpty()) {
			Node parent = Iterables.getFirst(sourceNodes, null).getParentNode();

			for (Node unmatchedPatchNode : unmatchedPatchNodes) {
				if (!exhaustedNodes.contains(unmatchedPatchNode)) {
					exhaustedNodes.add(unmatchedPatchNode);
					parent.appendChild(XPathUtils.cloneAndImport(unmatchedPatchNode, XPathUtils.getDocument(parent)));
				}
			}
		}

		return Lists.newArrayList();
	}

	private MergeHandler getDefaultMergeHandler() {
		MergeHandler handler = new AttributeReplaceInsert();
		handler.setName("Default Handler");
		return handler;
	}

	private Selector<Node> getSelector(final Node sourceNode, MergeMatcherType type) {
		switch (type) {
		case ID:
			return new NodeMatcher() {
				@Override
				public boolean test(Node input) {
					if (XPathUtils.attributeEqual(sourceNode, input, "id")) {
						return true;
					}
					if (XPathUtils.attributeEqual(sourceNode, input, "name")) {
						return true;
					}
					return false;
				}
			};
		case TYPE:
			return new NodeMatcher() {
				@Override
				public boolean test(Node input) {
					if (sourceNode.getNodeName().equals(input.getNodeName())) {
						return true;
					}
					return false;
				}
			};
		default:
			throw new RuntimeException("No node matcher implementation for type " + type);
		}
	}

	private List<Node> getNodes(String xPathVal, Object doc) throws XPathExpressionException {
		List<Node> nodes = Lists.newArrayList();
		NodeList temp1 = (NodeList) xPath.evaluate(xPathVal, doc, XPathConstants.NODESET);
		if (temp1 != null) {
			int length = temp1.getLength();
			for (int j = 0; j < length; j++) {
				nodes.add(temp1.item(j));
			}
		}

		return nodes;
	}
}