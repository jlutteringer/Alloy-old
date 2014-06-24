package org.vault.core.managed.system.time.domain;

public class DefaultTimeSource implements TimeSource {
	@Override
	public long timeInMillis() {
		return System.currentTimeMillis();
	}
}
