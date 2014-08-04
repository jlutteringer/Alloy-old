package org.alloy.cache.managed.statistics;

public interface StatisticsService {
	public void addCacheStat(String key, boolean isHit);

	public Long getLogResolution();

	public void setLogResolution(Long logResolution);
}
