package org.alloy.site.resource.handler;

import org.alloy.metal.spring.AlloyOrderableBean;
import org.springframework.beans.factory.annotation.Value;

public class AbstractResourceHandler extends AlloyOrderableBean {
	@Value("${project.version}")
	private String version;

	protected String getVersion() {
		return version;
	}
}
