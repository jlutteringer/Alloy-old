package org.vault.base.spring.beans.initialization;

import org.vault.base.spring.beans.AbstractVaultBean;
import org.vault.base.spring.beans.ordered.OrderableBean;

public abstract class AbstractInitializingBean extends AbstractVaultBean implements VaultInitializingBean, OrderableBean {

}
