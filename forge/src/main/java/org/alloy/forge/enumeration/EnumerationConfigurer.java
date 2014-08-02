package org.alloy.forge.enumeration;

import java.util.Collection;

import org.alloy.metal.enumeration._ExtendableEnumeration;
import org.alloy.metal.reflection._ClassPath;
import org.alloy.metal.reflection._Reflection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vault.base.utilities.constants.AlloyConfigurationConstants;

public class EnumerationConfigurer {
	private static Logger logger = LogManager.getLogger(EnumerationConfigurer.class);

	public static void configureEnumerations() {
		if (!_ExtendableEnumeration.isConfigured()) {
			Collection<EnumerationLoader> loaders =
					_Reflection.constructAll(_ClassPath.scanForType(AlloyConfigurationConstants.BOOTSTRAP_SCAN_BASE_PACKAGE, EnumerationLoader.class), true);

			loaders.forEach((element) -> {
				element.getEnumerationsToLoad().forEach((enumeration) -> {
					try {
						logger.debug("Loading enumeration: " + enumeration.getName());
						Class.forName(enumeration.getName());
					} catch (Exception e) {
						logger.error("Couldn't load specified enumeration", e);
					}
				});
			});

			_ExtendableEnumeration.endConfiguration();
		}
	}
}
