package org.alloy.site.resource.configuration;

public class TransformResourceConfiguration extends AbstractResourceConfiguration {
	private boolean injectProperties = false;

	public boolean isInjectProperties() {
		return injectProperties;
	}

	public void setInjectProperties(boolean injectProperties) {
		this.injectProperties = injectProperties;
	}
}
