package org.vault.persistence.managed;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.stereotype.Service;
import org.vault.base.reflection.VReflection;
import org.vault.persistence.managed.configuration.PersistenceMergeContext;

@Service("vaultPersistenceUnitManager")
public class MergePersistenceUnitManager extends DefaultPersistenceUnitManager {
	@Resource(name = "vaultDataSources")
	protected Map<String, DataSource> mergedDataSources;

	@Autowired
	private PersistenceMergeContext mergeContext;

	@PostConstruct
	private void initialize() {
		this.setDataSources(mergedDataSources);
	}

	@Override
	public void preparePersistenceUnitInfos() {
		ResourcePatternResolver resourcePatternResolver = VReflection.getField(this, "resourcePatternResolver", ResourcePatternResolver.class);
		DataSourceLookup dataSourceLookup = VReflection.getField(this, "dataSourceLookup", DataSourceLookup.class);

		PersistenceUnitReader reader = new PersistenceUnitReader(resourcePatternResolver, dataSourceLookup);
		List<PersistenceUnitInfo> persistenceUnits = reader.readPersistenceUnitInfo(mergeContext.getMergedResource());

		persistenceUnits.forEach((persistenceUnit) -> {
			VReflection.getMap(this, "persistenceUnitInfos", String.class, PersistenceUnitInfo.class)
					.put(persistenceUnit.getPersistenceUnitName(), persistenceUnit);
		});
	}

	@Override
	public PersistenceUnitInfo obtainPersistenceUnitInfo(String persistenceUnitName) {
		return VReflection.getMap(this, "persistenceUnitInfos", String.class, PersistenceUnitInfo.class).get(persistenceUnitName);
	}

	@Override
	public PersistenceUnitInfo obtainDefaultPersistenceUnitInfo() {
		throw new IllegalStateException("Default Persistence Unit is not supported. The persistence unit name must be specified at the entity manager factory.");
	}
}