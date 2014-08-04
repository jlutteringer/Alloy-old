package org.alloy.web.controller.login;

import org.alloy.site.controller.AlloyController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController extends AlloyController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}