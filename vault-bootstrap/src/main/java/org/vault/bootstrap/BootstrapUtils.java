package org.vault.bootstrap;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.vault.base.enumeration.VEnumerations;
import org.vault.base.reflection.VReflection;
import org.vault.base.utilities.constants.VConfigurationFileConstants;
import org.vault.base.utilities.spring.SpringClasspathUtils;
import org.vault.bootstrap.managed.logging.initialization.EnumerationLoader;

public class BootstrapUtils {
	private static final Logger log = Logger.getLogger(BootstrapUtils.class);

	public static void configureEnumerations() {
		if (!VEnumerations.isConfigured()) {
			Collection<EnumerationLoader> loaders =
					VReflection.constructAll(SpringClasspathUtils.scanForType(VConfigurationFileConstants.BOOTSTRAP_SCAN_BASE_PACKAGE, EnumerationLoader.class), true);

			loaders.forEach((element) -> {
				element.getEnumerationsToLoad().forEach((enumeration) -> {
					try {
						log.debug("Loading enumeration: " + enumeration.getName());
						Class.forName(enumeration.getName());
					} catch (Exception e) {
						log.error("Couldn't load specified enumeration", e);
					}
				});
			});

			VEnumerations.endConfiguration();
		}
	}
}