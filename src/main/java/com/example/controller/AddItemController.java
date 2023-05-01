package com.example.controller;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.example.domain.Category;
import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.AddItemForm;
import com.example.service.ResisterItemService;
import com.example.service.SelectCategoryService;

/**
 * 商品追加用コントローラー
 * 
 * @author yoshidayuuta
 *
 */
@Controller
@RequestMapping("/add")
public class AddItemController {

	@Autowired
	private ResisterItemService resisterItemService;
	@Autowired
	private SelectCategoryService selectCategoryService;

	/**
	 * 商品追加画面の表示、バリデーション
	 * 
	 * @param form  追加商品フォーム
	 * @param model モデル
	 * @return 商品追加画面
	 */
	@GetMapping("")
	public String index(@ModelAttribute AddItemForm form, Model model) {
		List<Category> parentCategoryList = selectCategoryService.selectParentCategory();
		model.addAttribute("parentCategoryList", parentCategoryList);
		return "add";
	}

	/**
	 * 商品追加
	 * 
	 * @param form   追加商品情報
	 * @param result バリデーション
	 * @param model  モデル
	 * @return 商品追加画面
	 * @throws IOException
	 */
	@PostMapping("/additem")
	public String addItem(@Validated @ModelAttribute("addItemForm") AddItemForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUser loginUser) throws IOException {
		User user = loginUser.getUser();
		if (result.hasErrors()) {
			return index(form, model);
		}
		form.setInsertUser(user.getId());
		resisterItemService.insertItem(form);
		return "redirect:/showitemlist";
	}
}
