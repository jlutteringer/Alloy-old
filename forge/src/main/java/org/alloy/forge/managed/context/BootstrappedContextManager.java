package org.alloy.forge.managed.context;

import java.util.List;

import org.alloy.forge.context.BootstrappedBean;
import org.alloy.forge.context.BootstrappedBeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// FUTURE review genericization
@Component
public class BootstrappedContextManager {
	@Autowired
	private List<BootstrappedBean> bootstrappedContextBeans;

	public BootstrappedBeanFactory createBootstrappedBeanFactory() {
		BootstrappedBeanFactory context = new BootstrappedBeanFactory();
		for (BootstrappedBean bean : bootstrappedContextBeans) {
			context.addBean(bean.getName(), bean);
		}
		return context;
	}
}