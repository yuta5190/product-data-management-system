package com.example.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.example.domain.Category;
import com.example.form.AddItemForm;
import com.example.service.AddItemService;
import com.example.service.ShowCategoryListService;


/**
 * 商品追加用コントローラー
 * @author yoshidayuuta
 *
 */
@Controller
@RequestMapping("/additem")
public class AddItemController {

	@Autowired
	private AddItemService addItemService;
	@Autowired
	private ShowCategoryListService showCategoryListService;


	/**
	 *商品追加画面の表示、バリデーション 
	 * @param form 追加商品フォーム
	 * @param model　モデル
	 * @return　商品追加画面
	 */
	@GetMapping("/view")
	public String view(@ModelAttribute AddItemForm form, Model model) {
		List<Category> parentCategoryList = showCategoryListService.viewParentCategory();
		model.addAttribute("parentCategoryList",parentCategoryList);		
		return "add";
	}

	/**
	 * 商品追加
	 * @param form　追加商品情報
	 * @param result　バリデーション
	 * @param model　モデル
	 * @return　商品追加画面
	 * @throws IOException
	 */
	@PostMapping("/submit")
	public String addItem(@Validated @ModelAttribute("addItemForm") AddItemForm form, BindingResult result,Model model) throws IOException {
		 if (result.hasErrors()) {return view(form, model);
		    }
		addItemService.insertItem(form);
		return "redirect:/showitemlist";
	}

	/**
	 * 中間カテゴリーの全件表示
	 * @param id　大カテゴリーid
	 * @return　中カテゴリー選択タブ
	 */
	@GetMapping("/viewchildcategory")
	@ResponseBody
	public Map<String, Object> showChildCategory(@RequestParam int id) {
		System.out.println(id);
		List<Category> childCategoryList = showCategoryListService.viewCategory(id,1);
		 Map<String, Object> data = new HashMap<>();
		    data.put("childCategoryList",childCategoryList);
		return data;
	}

	/**
	 * 小カテゴリーの全件表示
	 * @param id　中カテゴリーid
	 * @return　小カテゴリー選択タブ
	 */
	@GetMapping("/viewgrandchildcategory")
	@ResponseBody
	public Map<String, Object> showGrandChildCategory(int id) {
		System.out.println(id);
		List<Category> grandChildCategoryList = showCategoryListService.viewCategory(id,1);
		 Map<String, Object> granddata = new HashMap<>();
		 granddata.put("grandChildCategoryList",grandChildCategoryList);
		return granddata;
	}
}
