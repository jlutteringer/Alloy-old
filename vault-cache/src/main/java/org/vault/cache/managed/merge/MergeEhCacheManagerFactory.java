package org.vault.cache.managed.merge;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.vault.cache.managed.configuration.EhCacheMergeContext;

@Service
public class MergeEhCacheManagerFactory extends EhCacheManagerFactoryBean {
	@Autowired
	private EhCacheMergeContext mergeConfiguration;

	@PostConstruct
	public void configureMergedItems() {
		this.setConfigLocation(mergeConfiguration.getMergedResource());
	}
}
