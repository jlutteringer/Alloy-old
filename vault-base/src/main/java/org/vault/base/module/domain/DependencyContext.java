package org.vault.base.module.domain;

import java.util.List;

import org.vault.base.collections.CollectionMonad;

public class DependencyContext extends CollectionMonad<List<Dependency>, Dependency> {
	public DependencyContext(List<Dependency> collection) {
		super(collection);
	}
}