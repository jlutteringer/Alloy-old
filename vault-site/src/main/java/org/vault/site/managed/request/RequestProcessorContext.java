package org.vault.site.managed.request;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import org.vault.base.collections.lists.MutableLists;
import org.vault.base.collections.map._Map;

import com.google.common.collect.Multimap;

@Service
public class RequestProcessorContext {
	private Multimap<RequestLifecycle, RequestProcessor> processorMap = _Map.newMultiMap();

	@Autowired(required = false)
	private void setRequestProcessors(List<RequestProcessor> requestProcessors) {
		MutableLists.sort(requestProcessors);

		requestProcessors.forEach((requestProcessor) -> {
			processorMap.put(requestProcessor.getRequestLifecycle(), requestProcessor);
		});
	}

	public void process(RequestLifecycle lifecycle, ServletWebRequest servletWebRequest) {
		Collection<RequestProcessor> processors = null;
		if (RequestLifecycle.ALL.equals(lifecycle)) {
			processors = processorMap.values();
		}
		else {
			processors = processorMap.get(lifecycle);
		}

		processors.forEach((processor) -> processor.process(servletWebRequest));
	}
}