package org.alloy.metal.reflection;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import com.google.common.base.Throwables;
import com.google.common.collect.Lists;

public class _ClassPath {
	public static List<String> getClasspathEntries() {
		String[] classpathEntries = System.getProperty("java.class.path").split(File.pathSeparator);
		return Arrays.asList(classpathEntries);
	}

	@SuppressWarnings("unchecked")
	public static <T> List<Class<T>> scanForType(String basePackage, Class<T> type) {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);

		List<Class<T>> candidates = Lists.newArrayList();

		String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
				resolveBasePackage(basePackage) + "/" + "**/*.class";

		try {
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for (Resource resource : resources) {
				if (resource.isReadable()) {
					MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);

					Class<?> canidate = Class.forName(metadataReader.getClassMetadata().getClassName());
					if (type.isAssignableFrom(canidate)) {
						candidates.add((Class<T>) canidate);
					}
				}
			}
		} catch (Exception e) {
			Throwables.propagate(e);
		}

		return candidates;
	}

	private static String resolveBasePackage(String basePackage) {
		return ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage));
	}
}
