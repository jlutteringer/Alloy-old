package org.alloy.admin.controller;

import org.alloy.metal.spring.AlloyBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminLandingController extends AlloyBean {
	@RequestMapping("/admin")
	public String landing() {
		logger.debug("Hello world");
		return "adminLanding";
	}
}