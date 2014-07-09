package org.vault.web.managed.filter;

import org.vault.base.spring.beans.VaultBean;
import org.vault.base.spring.beans.ordered.PhaseOrderedBean;

public abstract class AbstractRequestProcessor extends VaultBean implements RequestProcessor, PhaseOrderedBean {

}
