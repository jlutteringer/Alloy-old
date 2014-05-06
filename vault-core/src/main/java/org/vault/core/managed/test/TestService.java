package org.vault.core.managed.test;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

@Service
public class TestService {
	@PostConstruct
	private void init() {
		System.out.println("Starting up test service");
	}

	public void test() {
		System.out.println("Test Success");
	}
}