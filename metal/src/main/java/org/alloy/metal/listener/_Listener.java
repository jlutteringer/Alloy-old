package org.alloy.metal.listener;

public class _Listener {
	public static <T, N extends Listener<T>> ListenerRegistry<T, N> createRegistry(T parent) {
		AbstractListenerRegistry<T, N> registry = new AbstractListenerRegistry<T, N>();
		registry.setParent(parent);
		return registry;
	}
}
