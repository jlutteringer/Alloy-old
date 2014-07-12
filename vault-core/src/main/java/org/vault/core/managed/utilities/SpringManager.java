package org.vault.core.managed.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

@Component
public class SpringManager {
	@Autowired
	private ApplicationContext applicationContext;

	public <T> List<Entry<String, T>> getOrderedBeansOfType(Class<T> type) {
		List<Entry<String, T>> beans = new ArrayList<Entry<String, T>>();
		Comparator<Entry<String, T>> comparator = new Comparator<Entry<String, T>>() {
			@Override
			public int compare(Entry<String, T> o1, Entry<String, T> o2) {
				return AnnotationAwareOrderComparator.INSTANCE.compare(o1.getValue(),
						o2.getValue());
			}
		};
		String[] names = applicationContext.getBeanNamesForType(type, true, false);
		Map<String, T> map = new LinkedHashMap<String, T>();
		for (String name : names) {
			map.put(name, applicationContext.getBean(name, type));
		}
		beans.addAll(map.entrySet());
		Collections.sort(beans, comparator);
		return beans;
	}
}
