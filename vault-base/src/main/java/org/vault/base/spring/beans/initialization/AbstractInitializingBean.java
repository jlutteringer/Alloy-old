package org.vault.base.spring.beans.initialization;

import org.vault.base.spring.beans.VaultBean;
import org.vault.base.spring.beans.ordered.OrderableBean;

public abstract class AbstractInitializingBean extends VaultBean implements VaultInitializingBean, OrderableBean {

}
