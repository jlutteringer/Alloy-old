package org.alloy.metal.collections.directory;

public interface DirectoryEntry<T, N> {
	public N getValue();

	public void setValue(N value);

	public T getKey();

	public void setIndex(DirectoryIndex<T, N> index);
}