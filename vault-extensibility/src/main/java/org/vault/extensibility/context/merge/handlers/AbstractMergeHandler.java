package org.vault.extensibility.context.merge.handlers;

import java.util.List;

import com.google.common.collect.Lists;

public abstract class AbstractMergeHandler implements MergeHandler {
	private int priority = 1;
	private String xPath = "*";
	private List<MergeHandler> children = Lists.newArrayList();
	private String name = "Default Name";
	private MergeMatcherType matcherType = MergeMatcherType.ID;

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public String getXPath() {
		return xPath;
	}

	public void setXPath(String xPath) {
		this.xPath = xPath;
	}

	public List<MergeHandler> getChildren() {
		return children;
	}

	public void setChildren(List<MergeHandler> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MergeMatcherType getMatcherType() {
		return matcherType;
	}

	public void setMatcherType(MergeMatcherType matcherType) {
		this.matcherType = matcherType;
	}
}
