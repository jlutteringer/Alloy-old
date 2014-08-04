package org.alloy.core.managed.system;

public class FixedTimeSource implements TimeSource {
	private final long timeInMillis;

	public FixedTimeSource(long timeInMillis) {
		this.timeInMillis = timeInMillis;
	}

	@Override
	public long timeInMillis() {
		return timeInMillis;
	}
}
