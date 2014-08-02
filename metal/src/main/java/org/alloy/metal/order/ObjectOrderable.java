package org.alloy.metal.order;

public interface ObjectOrderable<T extends Orderable> extends Orderable {
	public T getOrderingObject();

	@Override
	public default int getOrder() {
		return this.getOrderingObject().getOrder();
	}

	public static interface BiObjectOrderable<T extends Orderable, N extends Orderable> extends ObjectOrderable<T>, BiOrderable {
		public N getSecondaryOrderingObject();

		@Override
		public default int getSecondaryOrder() {
			return this.getSecondaryOrderingObject().getOrder();
		}
	}
}