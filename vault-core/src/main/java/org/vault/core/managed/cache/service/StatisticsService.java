package org.vault.core.managed.cache.service;

public interface StatisticsService {
	public void addCacheStat(String key, boolean isHit);

	public Long getLogResolution();

	public void setLogResolution(Long logResolution);
}
