package org.alloy.metal.collections.directory;

import java.util.List;

public interface DirectoryIndex<T, N> {
	public T getKey();

	public void setKey(T key);

	public List<DirectoryEntry<T, N>> getEntries();

	public long size();

	public DirectoryEntry<T, N> add(N value);
}
