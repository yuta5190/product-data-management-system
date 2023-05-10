package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Category;
import com.example.service.SelectCategoryService;
@Controller
@RequestMapping("/showcategorylist")
public class ShowCategoryController {

	@Autowired
	private SelectCategoryService selectCategoryService;
	/**
	 * 中間カテゴリーの全件表示
	 * 
	 * @param id 大カテゴリーid
	 * @return 中カテゴリー選択タブ
	 */
	@GetMapping("/viewchildcategory")
	@ResponseBody
	public Map<String, Object> viewChildCategory(@RequestParam int id) {
		Objects.requireNonNull(id, "/showcategorylist/viewchildcategory: id must not be null");
		List<Category> childCategoryList = selectCategoryService.selectCategory(id, 1);
		Map<String, Object> data = new HashMap<>();
		data.put("childCategoryList", childCategoryList);
		return data;
	}

	/**
	 * 小カテゴリーの全件表示
	 * 
	 * @param id 中カテゴリーid
	 * @return 小カテゴリー選択タブ
	 */
	@GetMapping("/viewgrandchildcategory")
	@ResponseBody
	public Map<String, Object> viewGrandChildCategory(int id) {
		Objects.requireNonNull(id, "/showcategorylist/viewgrandchildcategory: id must not be null");
		List<Category> grandChildCategoryList = selectCategoryService.selectCategory(id, 1);
		Map<String, Object> granddata = new HashMap<>();
		granddata.put("grandChildCategoryList", grandChildCategoryList);
		return granddata;
	}
}
