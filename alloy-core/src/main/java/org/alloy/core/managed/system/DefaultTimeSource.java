package org.alloy.core.managed.system;

public class DefaultTimeSource implements TimeSource {
	@Override
	public long timeInMillis() {
		return System.currentTimeMillis();
	}
}
