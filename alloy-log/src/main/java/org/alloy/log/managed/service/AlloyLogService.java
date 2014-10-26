package org.alloy.log.managed.service;

import java.time.Instant;
import java.util.List;

import org.alloy.log.domain.AlloyLog;
import org.alloy.log.domain.AlloyLogEntry;
import org.alloy.log.domain.AlloyLogEntryImpl;
import org.alloy.log.domain.AlloyLogImpl;
import org.alloy.metal.collections.iterable._Iterable;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.function._Function;
import org.alloy.metal.string._String;
import org.alloy.persistence.service.GenericDaoWrapper;
import org.alloy.persistence.utilities._Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlloyLogService extends GenericDaoWrapper<AlloyLog, AlloyLogImpl> {
	public AlloyLog findByName(String name) {
		return dao.find(AlloyLogImpl.class, _Query.where("entity.name = :name").setParameter("name", name));
	}

	@Transactional("primary")
	public AlloyLog begin() {
		AlloyLog log = this.save(this.create());
		log.setName(log.getId().toString());
		return this.save(log);
	}

	@Transactional("primary")
	public AlloyLog begin(String logName) {
		AlloyLog log = this.create();
		log.setName(logName);
		return this.save(log);
	}

	public void end(AlloyLog log) {

	}

	@Transactional("primary")
	public AlloyLogEntry log(AlloyLog log, String message, Object... parameters) {
		AlloyLogEntry entry = this.createEntry();
		entry.setMessage(_String.resolvePlaceholders(message, parameters));
		entry.setLog(log);
		entry = this.save(entry);
		return entry;
	}

	public Iterable<AlloyLogEntry> getLogEntries(AlloyLog log, Instant since, int limit) {
		if (log == null) {
			return _List.list();
		}

		List<AlloyLogEntryImpl> entries = this.dao.findAll(AlloyLogEntryImpl.class,
				_Query.where("entity.log = :log and entity.timeStamp.createdDate > :since")
						.setParameter("log", log)
						.setParameter("since", since)
						.limit(limit));

		return _Iterable.transform(entries, _Function.cast());
	}

	private AlloyLogEntry save(AlloyLogEntry entry) {
		return this.dao.save(entry);
	}

	private AlloyLogEntry createEntry() {
		return new AlloyLogEntryImpl();
	}
}