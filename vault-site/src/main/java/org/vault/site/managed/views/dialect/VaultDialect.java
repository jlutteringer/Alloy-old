package org.vault.site.managed.views.dialect;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.processor.IProcessor;
import org.vault.site.managed.views.processor.VaultDialectProcessor;

import com.google.common.collect.Sets;

@Service("vaultDialect")
public class VaultDialect extends AbstractDialect {
	@Autowired
	private Set<VaultDialectProcessor> processors = Sets.newHashSet();

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
		this.processors = (Set<VaultDialectProcessor>) (Set<?>) processors;
	}
}
