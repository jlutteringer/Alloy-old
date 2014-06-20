package org.vault.core.managed.test;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	private static Logger logger = LogManager.getLogger(TestService.class);

	@Value("${testProperty}")
	private String testProperty;

	@PostConstruct
	private void init() {
		logger.info("Starting up test service " + testProperty);
	}

	public void test() {
		System.out.println("Test Success");
	}
}