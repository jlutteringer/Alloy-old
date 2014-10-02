package org.alloy.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminUtilityController {
	@RequestMapping("/utilities/admin/template")
	public String template() {
		return "adminTemplate";
	}
}
