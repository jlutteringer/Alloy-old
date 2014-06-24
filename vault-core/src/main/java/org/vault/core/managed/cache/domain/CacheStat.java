package org.vault.core.managed.cache.domain;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

import org.vault.core.managed.system.time.domain.SystemTime;

public class CacheStat {

	protected AtomicLong requestCount = new AtomicLong(0L);
	protected AtomicLong cacheHitCount = new AtomicLong(0L);
	protected Long lastLogTime = SystemTime.asMillis(true);

	public Long getCacheHitCount() {
		return cacheHitCount.longValue();
	}

	public Long getLastLogTime() {
		return lastLogTime;
	}

	public synchronized void setLastLogTime(Long lastLogTime) {
		this.lastLogTime = lastLogTime;
	}

	public Long getRequestCount() {
		return requestCount.longValue();
	}

	public void incrementRequest() {
		requestCount.incrementAndGet();
	}

	public void incrementHit() {
		cacheHitCount.incrementAndGet();
	}

	public BigDecimal getHitRate() {
		if (getRequestCount() == 0) {
			return new BigDecimal(-1);
		}
		BigDecimal percentage = new BigDecimal(getCacheHitCount()).divide(new BigDecimal(getRequestCount
				()), 2, BigDecimal.ROUND_HALF_UP);
		percentage = percentage.multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
		return percentage;
	}
}
