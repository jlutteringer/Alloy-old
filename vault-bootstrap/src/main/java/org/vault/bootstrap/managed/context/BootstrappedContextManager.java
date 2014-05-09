package org.vault.bootstrap.managed.context;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vault.bootstrap.context.BootstrappedContext;
import org.vault.bootstrap.context.BootstrappedContextBean;

@Component
public class BootstrappedContextManager {
	@Autowired
	private List<BootstrappedContextBean> bootstrappedContextBeans;

	public BootstrappedContext createBootstrappedContext() {
		BootstrappedContext context = new BootstrappedContext();
		for (BootstrappedContextBean bean : bootstrappedContextBeans) {
			context.addBean(bean.getName(), bean);
		}
		return context;
	}
}