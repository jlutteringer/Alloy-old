package org.vault.site.resource.handler;

import org.springframework.beans.factory.annotation.Value;
import org.vault.base.spring.beans.VaultOrderableBean;

public class AbstractResourceHandler extends VaultOrderableBean {
	@Value("${project.version}")
	private String version;

	protected String getVersion() {
		return version;
	}
}
