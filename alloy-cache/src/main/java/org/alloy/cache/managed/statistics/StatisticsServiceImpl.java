package org.alloy.cache.managed.statistics;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

import org.alloy.core.managed.system.SystemTime;
import org.alloy.metal.spring.AlloyBean;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.naming.SelfNaming;
import org.springframework.jmx.support.ObjectNameManager;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImpl extends AlloyBean implements DynamicMBean, StatisticsService, SelfNaming {
	@Value("${cache.stat.log.resolution}")
	protected Long logResolution;

	protected String appName = "broadleaf";

	protected Map<String, CacheStat> cacheStats = Collections.synchronizedMap(new HashMap<String, CacheStat>());

	@Override
	public void addCacheStat(String key, boolean isHit) {
		CacheStat myStat = getCacheStat(key);
		if (isHit) {
			myStat.incrementHit();
		}
		myStat.incrementRequest();
		if (myStat.getLastLogTime() + logResolution < SystemTime.asMillis()) {
			myStat.setLastLogTime(SystemTime.asMillis());
			BigDecimal percentage = myStat.getHitRate();
			if (logger.isInfoEnabled()) {
				logger.info("Cache hit percentage for " + key + " is: " + percentage.toString() + "%");
			}
		}
	}

	protected CacheStat getCacheStat(String key) {
		if (!cacheStats.containsKey(key)) {
			CacheStat stat = new CacheStat();
			cacheStats.put(key, stat);
		}
		return cacheStats.get(key);
	}

	@Override
	public Long getLogResolution() {
		return logResolution;
	}

	@Override
	public void setLogResolution(Long logResolution) {
		this.logResolution = logResolution;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public Object getAttribute(String attribute) throws AttributeNotFoundException, MBeanException, ReflectionException {
		if (attribute.equals("LOG_RESOLUTION")) {
			return getLogResolution();
		}
		return getCacheStat(attribute).getHitRate().doubleValue();
	}

	@Override
	public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
		if (attribute.getName().equals("LOG_RESOLUTION")) {
			setLogResolution((Long) attribute.getValue());
		}
		// do nothing - not allowed
	}

	@Override
	public AttributeList getAttributes(String[] attributes) {
		AttributeList list = new AttributeList();
		for (Map.Entry<String, CacheStat> stats : cacheStats.entrySet()) {
			list.add(new Attribute(stats.getKey(), stats.getValue().getHitRate().doubleValue()));
		}
		return list;
	}

	@Override
	public AttributeList setAttributes(AttributeList attributes) {
		for (Object attr : attributes) {
			try {
				setAttribute((Attribute) attr);
			} catch (Exception e) {
				logger.error("cannot set attribute: " + ((Attribute) attr).getName(), e);
			}
		}
		return attributes;
	}

	@Override
	public Object invoke(String actionName, Object[] params, String[] signature) throws MBeanException, ReflectionException {
		throw new MBeanException(new RuntimeException("Not Supported"));
	}

	@Override
	public ObjectName getObjectName() throws MalformedObjectNameException {
		return ObjectNameManager.getInstance("org.broadleafcommerce:name=StatisticsService." + appName);
	}

	@Override
	public MBeanInfo getMBeanInfo() {
		SortedSet<String> names = new TreeSet<String>();
		for (Map.Entry<String, CacheStat> stats : cacheStats.entrySet()) {
			names.add(stats.getKey());
		}
		MBeanAttributeInfo[] attrs = new MBeanAttributeInfo[names.size()];
		Iterator<String> it = names.iterator();
		for (int i = 0; i < attrs.length; i++) {
			String name = it.next();
			attrs[i] = new MBeanAttributeInfo(
					name,
					"java.lang.Double",
					name,
					true, // isReadable
					false, // isWritable
					false); // isIs
		}
		attrs = ArrayUtils.add(attrs, new MBeanAttributeInfo(
				"LOG_RESOLUTION",
				"java.lang.Double",
				"LOG_RESOLUTION",
				true, // isReadable
				true, // isWritable
				false) // isIs
				);
		MBeanOperationInfo[] opers = {
				new MBeanOperationInfo(
						"activate",
						"Activate statistic logging",
						null, // no parameters
						"void",
						MBeanOperationInfo.ACTION),
				new MBeanOperationInfo(
						"disable",
						"Disable statistic logging",
						null, // no parameters
						"void",
						MBeanOperationInfo.ACTION)
		};
		return new MBeanInfo(
				"org.broadleafcommerce:name=StatisticsService." + appName,
				"Runtime Statistics",
				attrs,
				null, // constructors
				opers,
				null); // notifications
	}
}
