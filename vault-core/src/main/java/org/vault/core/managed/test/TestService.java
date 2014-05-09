package org.vault.core.managed.test;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TestService {
	@Value("${testProperty}")
	private String testProperty;

	@PostConstruct
	private void init() {
		System.out.println("Starting up test service " + testProperty);
	}

	public void test() {
		System.out.println("Test Success");
	}
}