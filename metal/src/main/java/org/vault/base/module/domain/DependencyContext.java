package org.vault.base.module.domain;

import java.util.List;

import org.alloy.metal.collections.CollectionBuilder;

import com.google.common.collect.Lists;

public class DependencyContext extends CollectionBuilder<List<Dependency>, Dependency> {
	public DependencyContext() {
		super(Lists.newArrayList());
	}

	public DependencyContext(List<Dependency> collection) {
		super(collection);
	}
}