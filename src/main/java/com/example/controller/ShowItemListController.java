package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SelectItemForm;
import com.example.service.ShowCategoryListService;
import com.example.service.ShowItemListService;

/**
 * 商品一覧表示画面のコントローラー
 * 
 * @author yoshidayuuta
 *
 */
@Controller
@RequestMapping("/showitemlist")
public class ShowItemListController {
	@Autowired
	private ShowItemListService showItemlistService;
	@Autowired
	private ShowCategoryListService showCategoryListService;
	
	/**
	 * 画面表示＋大カテゴリーリスト表示
	 * 
	 * @param model モデル @param form 商品の絞り込みフォーム
	 * @return 商品一覧画面
	 */
	@GetMapping("")
	public String index(Model model, SelectItemForm form) {
		List<Category> parentCategoryList = showCategoryListService.viewParentCategory();
		model.addAttribute("parentCategoryList", parentCategoryList);
		List<Item> itemList = showItemlistService.showItemList(1, "", null, null, null, "", 1000, "");
		model.addAttribute("itemList", itemList);
		Category a = new Category();
		Category b = new Category();
		model.addAttribute("childCategory", a);
		model.addAttribute("grandChildCategory", b);
		return "list";
	}

	
	/**
	 * １ページ目表示、アイテム数による総ページ計算
	 * 
	 * @return １ページ目表示に関わるJavaScript
	 */
	@GetMapping("/viewlist")
	@ResponseBody
	public Map<String, Object> viewList(String search, Integer parentCategory, Integer childCategory,
			Integer grandChildCategory, String brand) {
		if (search == null) {
			search = "";
		}
		if (brand == null) {
			brand = "";
		}
		Map<String, Object> firstPageData = new HashMap<>();
		Integer totalPage = showItemlistService.totalPageCount(search, parentCategory, childCategory,
				grandChildCategory, brand);
		firstPageData.put("totalPage", totalPage);
		return firstPageData;
	}

	
	/**
	 * 表示ページに基づく商品リスト表示
	 * 
	 * @param page 表示ページ
	 * @return 商品を表示するJavaScript
	 */
	@GetMapping("/paging")
	@ResponseBody
	public Map<String, Object> paging(Integer page, String search, Integer parentCategory, Integer childCategory,
			Integer grandChildCategory, String brand, Integer totalPage, String orderBy) {
		if (search == null) {
			search = "";
		}
		if (brand == null) {
			brand = "";
		}
		if (orderBy == null) {
			orderBy = "";
		}
		List<Item> itemList = showItemlistService.showItemList(page, search, parentCategory, childCategory,
		grandChildCategory, brand, totalPage, orderBy);
		Map<String, Object> data = new HashMap<>();
		data.put("itemList", itemList);
		return data;
	}
}