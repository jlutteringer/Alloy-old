package org.alloy.site.managed.views.dialect;

import java.util.Set;

import org.alloy.site.managed.views.processor.AlloyDialectProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;

import com.google.common.collect.Sets;

@Service("alloyDialect")
public class AlloyDialect extends AbstractDialect {
	@Autowired
	private Set<AlloyDialectProcessor> processors = Sets.newHashSet();

	@Override
	public String getPrefix() {
		return "v";
	}

	@Override
	public boolean isLenient() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IProcessor> getProcessors() {
		return (Set<IProcessor>) (Set<?>) processors;
	}

	@SuppressWarnings("unchecked")
	public void setProcessors(Set<IProcessor> processors) {
		this.processors = (Set<AlloyDialectProcessor>) (Set<?>) processors;
	}
}
