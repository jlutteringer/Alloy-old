package org.vault.site.filter;

import javax.servlet.Filter;

import org.vault.site.managed.request.RequestLifecycleOrdeable;

public interface VaultFilter extends Filter, RequestLifecycleOrdeable {

}