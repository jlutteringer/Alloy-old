package org.vault.persistence.managed;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.lookup.DataSourceLookup;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.util.xml.DomUtils;
import org.vault.base.utilities.exception.Exceptions;
import org.vault.base.utilities.stream.VStreams;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class PersistenceUnitReader {
	private static final String PERSISTENCE_VERSION = "version";
	private static final String PERSISTENCE_UNIT = "persistence-unit";
	private static final String UNIT_NAME = "name";
	private static final String MAPPING_FILE_NAME = "mapping-file";
	private static final String JAR_FILE_URL = "jar-file";
	private static final String MANAGED_CLASS_NAME = "class";
	private static final String PROPERTIES = "properties";
	private static final String PROVIDER = "provider";
	private static final String TRANSACTION_TYPE = "transaction-type";
	private static final String JTA_DATA_SOURCE = "jta-data-source";
	private static final String NON_JTA_DATA_SOURCE = "non-jta-data-source";
	private static final String EXCLUDE_UNLISTED_CLASSES = "exclude-unlisted-classes";
	private static final String SHARED_CACHE_MODE = "shared-cache-mode";
	private static final String VALIDATION_MODE = "validation-mode";

	private final Logger logger = LogManager.getLogger(this.getClass());

	private final ResourcePatternResolver resourcePatternResolver;

	private final DataSourceLookup dataSourceLookup;

	/**
	 * Create a new PersistenceUnitReader.
	 * @param resourcePatternResolver the ResourcePatternResolver to use for loading resources
	 * @param dataSourceLookup the DataSourceLookup to resolve DataSource names in
	 * {@code persistence.xml} files against
	 */
	public PersistenceUnitReader(ResourcePatternResolver resourcePatternResolver, DataSourceLookup dataSourceLookup) {
		Assert.notNull(resourcePatternResolver, "ResourceLoader must not be null");
		Assert.notNull(dataSourceLookup, "DataSourceLookup must not be null");
		this.resourcePatternResolver = resourcePatternResolver;
		this.dataSourceLookup = dataSourceLookup;
	}

	public List<PersistenceUnitInfo> readPersistenceUnitInfo(Resource persistenceUnit) {
		List<PersistenceUnitInfo> infos = Lists.newArrayList();

		VStreams.withStream(VStreams.transformer(), persistenceUnit, (stream) -> {
			Document document = buildDocument(VStreams.transformer().apply(persistenceUnit));
			parseDocument(persistenceUnit, document, infos);
		});

		return infos;
	}

	/**
	 * Validate the given stream and return a valid DOM document for parsing.
	 */
	protected Document buildDocument(InputStream stream) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder parser = dbf.newDocumentBuilder();
			return parser.parse(stream);
		} catch (Exception e) {
			throw Throwables.propagate(e);
		}
	}

	/**
	 * Parse the validated document and add entries to the given unit info list.
	 */
	protected List<PersistenceUnitInfo> parseDocument(Resource resource, Document document, List<PersistenceUnitInfo> infos) {
		Element persistence = document.getDocumentElement();
		String version = persistence.getAttribute(PERSISTENCE_VERSION);

		List<Element> units = DomUtils.getChildElementsByTagName(persistence, PERSISTENCE_UNIT);
		for (Element unit : units) {
			infos.add(parsePersistenceUnitInfo(unit, version));
		}

		return infos;
	}

	/**
	 * Parse the unit info DOM element.
	 */
	protected MutablePersistenceUnitInfo parsePersistenceUnitInfo(Element persistenceUnit, String version) {

		MutablePersistenceUnitInfo unitInfo = new MutablePersistenceUnitInfo();

		// set JPA version (1.0 or 2.0)
		unitInfo.setPersistenceXMLSchemaVersion(version);

		// set persistence unit root URL
//		unitInfo.setPersistenceUnitRootUrl(rootUrl);

		// set unit name
		unitInfo.setPersistenceUnitName(persistenceUnit.getAttribute(UNIT_NAME).trim());

		// set transaction type
		String txType = persistenceUnit.getAttribute(TRANSACTION_TYPE).trim();
		if (StringUtils.hasText(txType)) {
			unitInfo.setTransactionType(PersistenceUnitTransactionType.valueOf(txType));
		}

		// evaluate data sources
		String jtaDataSource = DomUtils.getChildElementValueByTagName(persistenceUnit, JTA_DATA_SOURCE);
		if (StringUtils.hasText(jtaDataSource)) {
			unitInfo.setJtaDataSource(this.dataSourceLookup.getDataSource(jtaDataSource.trim()));
		}

		String nonJtaDataSource = DomUtils.getChildElementValueByTagName(persistenceUnit, NON_JTA_DATA_SOURCE);
		if (StringUtils.hasText(nonJtaDataSource)) {
			unitInfo.setNonJtaDataSource(this.dataSourceLookup.getDataSource(nonJtaDataSource.trim()));
		}

		// provider
		String provider = DomUtils.getChildElementValueByTagName(persistenceUnit, PROVIDER);
		if (StringUtils.hasText(provider)) {
			unitInfo.setPersistenceProviderClassName(provider.trim());
		}

		// exclude unlisted classes
		Element excludeUnlistedClasses = DomUtils.getChildElementByTagName(persistenceUnit, EXCLUDE_UNLISTED_CLASSES);
		if (excludeUnlistedClasses != null) {
			String excludeText = DomUtils.getTextValue(excludeUnlistedClasses);
			unitInfo.setExcludeUnlistedClasses(!StringUtils.hasText(excludeText) || Boolean.valueOf(excludeText));
		}

		// set JPA 2.0 shared cache mode
		String cacheMode = DomUtils.getChildElementValueByTagName(persistenceUnit, SHARED_CACHE_MODE);
		if (StringUtils.hasText(cacheMode)) {
			unitInfo.setSharedCacheMode(SharedCacheMode.valueOf(cacheMode));
		}

		// set JPA 2.0 validation mode
		String validationMode = DomUtils.getChildElementValueByTagName(persistenceUnit, VALIDATION_MODE);
		if (StringUtils.hasText(validationMode)) {
			unitInfo.setValidationMode(ValidationMode.valueOf(validationMode));
		}

		parseProperties(persistenceUnit, unitInfo);
		parseManagedClasses(persistenceUnit, unitInfo);
		parseMappingFiles(persistenceUnit, unitInfo);
		parseJarFiles(persistenceUnit, unitInfo);

		return unitInfo;
	}

	/**
	 * Parse the {@code property} XML elements.
	 */
	protected void parseProperties(Element persistenceUnit, MutablePersistenceUnitInfo unitInfo) {
		Element propRoot = DomUtils.getChildElementByTagName(persistenceUnit, PROPERTIES);
		if (propRoot == null) {
			return;
		}
		List<Element> properties = DomUtils.getChildElementsByTagName(propRoot, "property");
		for (Element property : properties) {
			String name = property.getAttribute("name");
			String value = property.getAttribute("value");
			unitInfo.addProperty(name, value);
		}
	}

	/**
	 * Parse the {@code class} XML elements.
	 */
	protected void parseManagedClasses(Element persistenceUnit, MutablePersistenceUnitInfo unitInfo) {
		List<Element> classes = DomUtils.getChildElementsByTagName(persistenceUnit, MANAGED_CLASS_NAME);
		for (Element element : classes) {
			String value = DomUtils.getTextValue(element).trim();
			if (StringUtils.hasText(value))
				unitInfo.addManagedClassName(value);
		}
	}

	/**
	 * Parse the {@code mapping-file} XML elements.
	 */
	protected void parseMappingFiles(Element persistenceUnit, MutablePersistenceUnitInfo unitInfo) {
		List<Element> files = DomUtils.getChildElementsByTagName(persistenceUnit, MAPPING_FILE_NAME);
		for (Element element : files) {
			String value = DomUtils.getTextValue(element).trim();
			if (StringUtils.hasText(value)) {
				unitInfo.addMappingFileName(value);
			}
		}
	}

	/**
	 * Parse the {@code jar-file} XML elements.
	 */
	protected void parseJarFiles(Element persistenceUnit, MutablePersistenceUnitInfo unitInfo) {
		Exceptions.propagate(() -> {
			List<Element> jars = DomUtils.getChildElementsByTagName(persistenceUnit, JAR_FILE_URL);
			for (Element element : jars) {
				String value = DomUtils.getTextValue(element).trim();
				if (StringUtils.hasText(value)) {
					Resource[] resources = this.resourcePatternResolver.getResources(value);
					boolean found = false;
					for (Resource resource : resources) {
						if (resource.exists()) {
							found = true;
							unitInfo.addJarFileUrl(resource.getURL());
						}
					}

					if (!found) {
						URL rootUrl = unitInfo.getPersistenceUnitRootUrl();
						if (rootUrl != null) {
							unitInfo.addJarFileUrl(new URL(rootUrl, value));
						}
						else {
							logger.warn("Cannot resolve jar-file entry [" + value + "] in persistence unit '" +
									unitInfo.getPersistenceUnitName() + "' without root URL");
						}
					}
				}
			}
		});
	}
}