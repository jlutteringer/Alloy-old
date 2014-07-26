package org.vault.boot.application;

import java.util.Collection;
import java.util.EventListener;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.MultipartConfigElement;
import javax.servlet.Servlet;

import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletListenerRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.XmlEmbeddedWebApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.vault.base.reflection.VReflection;
import org.vault.bootstrap.context.BootstrappedContext;
import org.vault.bootstrap.context.VaultApplicationContext;

public class VaultEmbeddedWebApplicationContext extends XmlEmbeddedWebApplicationContext implements VaultApplicationContext {
	private BootstrappedContext initializationContext;

	public VaultEmbeddedWebApplicationContext(Resource resource) {
		this.load(resource);
	}

	@Override
	public BootstrappedContext getInitializationContext() {
		return initializationContext;
	}

	@Override
	public void setInitializationContext(BootstrappedContext initializationContext) {
		this.initializationContext = initializationContext;
	}

	@Override
	protected ResourcePatternResolver getResourcePatternResolver() {
		return new PathMatchingResourcePatternResolver(this.getClassLoader());
	}

	@Override
	protected Collection<ServletContextInitializer> getServletContextInitializerBeans() {
		logger.debug("Gathering servlet context initializer beans...");
		Set<ServletContextInitializer> initializers = new LinkedHashSet<ServletContextInitializer>();
		Set<Servlet> servletRegistrations = new LinkedHashSet<Servlet>();
		Set<Filter> filterRegistrations = new LinkedHashSet<Filter>();
		Set<EventListener> listenerRegistrations = new LinkedHashSet<EventListener>();

		for (Entry<String, ServletContextInitializer> initializerBean : inspectOrderedBeansOfType(ServletContextInitializer.class)) {
			ServletContextInitializer initializer = initializerBean.getValue();
			initializers.add(initializer);
			if (initializer instanceof ServletRegistrationBean) {
				ServletRegistrationBean servlet = (ServletRegistrationBean) initializer;
				servletRegistrations.add(getServlet(servlet));
			}
			if (initializer instanceof FilterRegistrationBean) {
				FilterRegistrationBean filter = (FilterRegistrationBean) initializer;
				logger.debug("Detected FilterRegistrationBean [" + filter + "] adding to filter registrations");
				filterRegistrations.add(getFilter(filter));
			}
			if (initializer instanceof ServletListenerRegistrationBean) {
				listenerRegistrations
						.add(((ServletListenerRegistrationBean<?>) initializer)
								.getListener());
			}
		}

		List<Entry<String, Servlet>> servletBeans = inspectOrderedBeansOfType(Servlet.class);
		for (Entry<String, Servlet> servletBean : servletBeans) {
			final String name = servletBean.getKey();
			Servlet servlet = servletBean.getValue();
			if (!servletRegistrations.contains(servlet)) {
				String url = (servletBeans.size() == 1 ? "/" : "/" + name + "/");
				if (name.equals(DISPATCHER_SERVLET_NAME)) {
					url = "/"; // always map the main dispatcherServlet to "/"
				}
				ServletRegistrationBean registration = new ServletRegistrationBean(
						servlet, url);
				registration.setName(name);
				registration.setMultipartConfig(inspectMultipartConfig());
				initializers.add(registration);
			}
		}

		Set<Class<?>> listenerTypes = ServletListenerRegistrationBean.getSupportedTypes();
		for (Class<?> type : listenerTypes) {
			for (Entry<String, ?> listenerBean : inspectOrderedBeansOfType(type)) {
				String name = listenerBean.getKey();
				EventListener listener = (EventListener) listenerBean.getValue();
				if (ServletListenerRegistrationBean.isSupportedType(listener)
						&& !filterRegistrations.contains(listener)) {
					ServletListenerRegistrationBean<EventListener> registration = new ServletListenerRegistrationBean<EventListener>(
							listener);
					registration.setName(name);
					initializers.add(registration);
				}
			}
		}

		return initializers;
	}

	@SuppressWarnings("unchecked")
	private <T> List<Entry<String, T>> inspectOrderedBeansOfType(Class<T> type) {
		return VReflection.invoke(this, "getOrderedBeansOfType", List.class, type);
	}

	private MultipartConfigElement inspectMultipartConfig() {
		return VReflection.invoke(this, "getMultipartConfig", MultipartConfigElement.class);
	}

	private Filter getFilter(FilterRegistrationBean filter) {
		return VReflection.invoke(filter, "getFilter", Filter.class);
	}

	private Servlet getServlet(ServletRegistrationBean servlet) {
		return VReflection.invoke(servlet, "getServlet", Servlet.class);
	}
}
