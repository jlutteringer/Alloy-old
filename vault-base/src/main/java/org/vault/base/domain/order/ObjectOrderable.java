package org.vault.base.domain.order;

public interface ObjectOrderable<T extends Orderable> extends Orderable {
	public T getPhase();

	@Override
	public default int getOrder() {
		return this.getPhase().getOrder();
	}

	public static interface BiObjectOrderable<T extends Orderable, N extends Orderable> extends ObjectOrderable<T>, BiOrderable {
		public N getSecondaryPhase();

		@Override
		public default int getSecondaryOrder() {
			return this.getSecondaryPhase().getOrder();
		}
	}
}