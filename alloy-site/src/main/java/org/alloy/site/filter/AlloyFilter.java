package org.alloy.site.filter;

import javax.servlet.Filter;

import org.alloy.site.managed.request.RequestLifecycleOrdeable;

public interface AlloyFilter extends Filter, RequestLifecycleOrdeable {

}