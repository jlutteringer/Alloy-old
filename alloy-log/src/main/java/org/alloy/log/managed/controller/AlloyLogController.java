package org.alloy.log.managed.controller;

import java.time.Instant;
import java.util.List;

import org.alloy.log.domain.AlloyLog;
import org.alloy.log.domain.AlloyLogEntry;
import org.alloy.log.managed.service.AlloyLogService;
import org.alloy.metal.collections.lists._List;
import org.alloy.metal.json.JsonStatus;
import org.alloy.metal.json._Json;
import org.alloy.metal.utilities._Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AlloyLogController {
	private static final String LOG_API_URL = "/alloy/api/log";

	@Autowired
	private AlloyLogService logService;

	@RequestMapping(value = LOG_API_URL + "/{logName}/{message}", method = RequestMethod.GET)
	@ResponseBody
	public JsonStatus addLogEntry(@PathVariable String logName, @PathVariable String message) {
		AlloyLog log = logService.findByName(logName);
		if (log == null) {
			log = logService.begin(logName);
		}

		logService.log(log, message);
		return _Json.success();
	}

	@RequestMapping(value = LOG_API_URL + "/{logName}", method = RequestMethod.GET)
	@ResponseBody
	public AlloyLogContainer getLogEntries(@PathVariable String logName,
			@RequestParam(required = false) Instant since,
			@RequestParam(required = false, defaultValue = "100") int limit) {

		if (since == null) {
			since = _Date.MIN;
		}

		AlloyLog log = logService.findByName(logName);
		Iterable<AlloyLogEntry> entries = logService.getLogEntries(log, since, limit);

		return new AlloyLogContainer(log, _List.list(entries));
	}

	public static class AlloyLogContainer {
		private AlloyLog log;
		private List<AlloyLogEntry> entries;

		public AlloyLogContainer(AlloyLog log, List<AlloyLogEntry> entries) {
			super();
			this.log = log;
			this.entries = entries;
		}

		public AlloyLog getLog() {
			return log;
		}

		public List<AlloyLogEntry> getEntries() {
			return entries;
		}
	}
}