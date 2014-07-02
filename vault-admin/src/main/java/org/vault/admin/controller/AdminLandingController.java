package org.vault.admin.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminLandingController {
	public static Logger logger = LogManager.getLogger(AdminLandingController.class);

	@RequestMapping("/admin")
	public String landing() {
		logger.debug("Hello world");
		return "test";
	}
}