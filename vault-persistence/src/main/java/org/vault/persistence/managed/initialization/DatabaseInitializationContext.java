package org.vault.persistence.managed.initialization;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DatabaseInitializationContext {
	@Autowired
	private List<DatabaseInitializer> initializers;

	@PostConstruct
	private void initializeDatabase() {
		initializers.forEach((initializer) -> initializer.run());
	}

	public interface DatabaseInitializer extends Runnable {
	}

	@Service
	public static class TableSequenceGenerationInitializer implements DatabaseInitializer {
		@Resource(name = "vaultDataSources")
		protected Map<String, DataSource> mergedDataSources;

		@Override
		@Transactional("primary")
		public void run() {
			JdbcTemplate primary = new JdbcTemplate(mergedDataSources.get("jdbc/primary"));

			primary.update("CREATE TABLE IF NOT EXISTS `sequenceGenerator` (" +
					"`idName` varchar(255) DEFAULT NULL, " +
					"`idVal` int(11) DEFAULT NULL)");
		}
	}
}