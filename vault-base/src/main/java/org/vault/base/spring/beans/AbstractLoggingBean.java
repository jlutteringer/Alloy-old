package org.vault.base.spring.beans;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractLoggingBean {
	protected Logger logger = LogManager.getLogger(this.getClass());
}