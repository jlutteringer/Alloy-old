package org.vault.web.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.vault.site.controller.VaultController;

@Controller
public class LoginController extends VaultController {
	@RequestMapping("/login")
	public String login() {
		return "login";
	}
}