package org.vault.site.controller.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
	private static final Logger logger = LogManager.getLogger(TestController.class);

	@RequestMapping("/test")
	public String home() {
		logger.debug("Hello World!");
		return "test";
	}
}