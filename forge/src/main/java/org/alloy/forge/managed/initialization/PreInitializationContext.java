package org.alloy.forge.managed.initialization;

import java.util.Collections;
import java.util.List;

import org.alloy.metal.order.Orderable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//TODO evaluate genericizm
@Component
public class PreInitializationContext {
	@Autowired
	private List<PreInitializingBean> handlers;

	public void initialize() {
		Collections.sort(handlers, Orderable.comparator());

		for (PreInitializingBean handler : handlers) {
			handler.initialize();
		}
	}
}
