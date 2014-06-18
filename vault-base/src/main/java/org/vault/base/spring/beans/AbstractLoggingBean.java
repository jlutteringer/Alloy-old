package org.vault.base.spring.beans;

import org.apache.log4j.Logger;

public abstract class AbstractLoggingBean {
	protected Logger log = Logger.getLogger(this.getClass());
}