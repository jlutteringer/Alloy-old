package org.vault.base.spring.beans;

import org.apache.log4j.Logger;

public abstract class AbstractLoggingBean extends AbstractVaultBean {
	protected Logger log = Logger.getLogger(this.getClass());
}