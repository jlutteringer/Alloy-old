package org.alloy.site.filter;

import javax.servlet.Filter;

import org.alloy.site.request.RequestLifecycleOrdeable;

public interface AlloyFilter extends Filter, RequestLifecycleOrdeable {

}