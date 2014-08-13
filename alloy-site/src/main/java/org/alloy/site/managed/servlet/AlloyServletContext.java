package org.alloy.site.managed.servlet;

import javax.servlet.ServletContext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AlloyServletContext implements ApplicationContextAware {
	private static volatile ApplicationContext applicationContext;
	private static volatile AlloyServletContext reference;

	@Autowired
	private ServletContext servletContext;

	public ServletContext getServletContext() {
		return servletContext;
	}

	public static AlloyServletContext get() {
		if (reference == null) {
			reference = applicationContext.getBean(AlloyServletContext.class);
		}
		return reference;
	}

	public static boolean initialized() {
		if (applicationContext == null) {
			return false;
		}
		return true;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		AlloyServletContext.applicationContext = applicationContext;
	}
}