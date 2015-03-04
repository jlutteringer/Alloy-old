package org.alloy.content.managed.controller;

import org.alloy.content.category.domain.NavigationCategory;
import org.alloy.content.category.domain.NavigationCategoryEntity;
import org.alloy.content.category.domain.NavigationCategoryFragment;
import org.alloy.content.managed.category.service.NavigationCategoryService;
import org.alloy.metal.json.JsonStatus;
import org.alloy.metal.json._Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoryController {
	private static final String CATEGORY_API_URL = "/alloy/api/category";

	@Autowired
	private NavigationCategoryService categoryService;

	@RequestMapping(value = CATEGORY_API_URL, method = RequestMethod.GET)
	@ResponseBody
	public NavigationCategory getCategory(@RequestParam String key) {
		// FUTURE improve error handling
		return categoryService.findByName(key).get();
	}

	@RequestMapping(value = CATEGORY_API_URL, method = RequestMethod.POST)
	@ResponseBody
	public NavigationCategoryEntity createCategory(@RequestBody NavigationCategoryFragment categoryFragment) {
		return null;
	}

	@RequestMapping(value = CATEGORY_API_URL, method = RequestMethod.DELETE)
	@ResponseBody
	public JsonStatus removeCategory(@RequestParam String name) {
		// FUTURE improve error handling
		NavigationCategoryFragment category =
				categoryService.findFragmentByName(name).get();

		if (category instanceof NavigationCategoryEntity) {
			categoryService.remove((NavigationCategoryEntity) category);
			return _Json.success();
		}

		return _Json.failure("Category {} cannot be deleted because it is not an entity", name);
	}
}