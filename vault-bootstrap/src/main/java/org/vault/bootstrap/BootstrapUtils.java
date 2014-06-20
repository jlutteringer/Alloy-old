package org.vault.bootstrap;

import java.util.Collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vault.base.enumeration.VEnumerations;
import org.vault.base.reflection.VReflection;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.base.utilities.spring.SpringClasspathUtils;
import org.vault.bootstrap.managed.logging.initialization.EnumerationLoader;

public class BootstrapUtils {
	private static Logger logger = LogManager.getLogger(BootstrapUtils.class);

	public static void configureEnumerations() {
		if (!VEnumerations.isConfigured()) {
			Collection<EnumerationLoader> loaders =
					VReflection.constructAll(SpringClasspathUtils.scanForType(VConfigurationFileConstants.BOOTSTRAP_SCAN_BASE_PACKAGE, EnumerationLoader.class), true);

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

			VEnumerations.endConfiguration();
		}
	}
}