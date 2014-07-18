package org.vault.site.managed.request;

import org.vault.base.spring.beans.VaultBean;
import org.vault.base.spring.beans.ordered.PhaseOrderedBean;

public abstract class AbstractRequestProcessor extends VaultBean implements RequestProcessor, PhaseOrderedBean {

}
