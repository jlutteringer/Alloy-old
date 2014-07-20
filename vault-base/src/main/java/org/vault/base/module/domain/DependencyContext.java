package org.vault.base.module.domain;

import java.util.List;

import org.vault.base.collections.CollectionMonad;

import com.google.common.collect.Lists;

public class DependencyContext extends CollectionMonad<List<Dependency>, Dependency> {
	public DependencyContext() {
		super(Lists.newArrayList());
	}

	public DependencyContext(List<Dependency> collection) {
		super(collection);
	}
}