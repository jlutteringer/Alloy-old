package org.vault.base.observer;

public class Listeners {
	public static <T, N extends Listener<T>> ListenerRegistry<T, N> createRegistry(T parent) {
		AbstractListenerRegistry<T, N> registry = new AbstractListenerRegistry<T, N>();
		registry.setParent(parent);
		return registry;
	}
}
