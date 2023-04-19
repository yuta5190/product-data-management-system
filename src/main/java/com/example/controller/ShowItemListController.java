package com.example.controller;

import java.util.ArrayList;
import java.util.Collections;
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
import com.example.service.PagingService;
import com.example.service.SelectCategoryService;
import com.example.service.ShowItemListService;
import com.example.service.ShowSelectedItemListService;

import ch.qos.logback.core.recovery.ResilientSyslogOutputStream;

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
	private ShowSelectedItemListService showSelectedItemListService;
	@Autowired
	private SelectCategoryService selectCategoryService;
	@Autowired
	public PagingService pagingService;

	/**
	 * 画面表示＋大カテゴリーリスト表示
	 * 
	 * @param model モデル @param form 商品の絞り込みフォーム
	 * @return 商品一覧画面
	 */
	@GetMapping("")
	public String index(Model model, SelectItemForm form) {
		List<Category> parentCategoryList = selectCategoryService.viewParentCategory();
		model.addAttribute("parentCategoryList", parentCategoryList);
		List<Item> itemList = showItemlistService.showItemList(1, "", 0, 0, 0, "", 1000, "");
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
		Integer totalPage = pagingService.totalPageCount(search, parentCategory, childCategory, grandChildCategory,
				brand);
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

	/**
	 * 商品絞り込みフォームによる商品リスト表示
	 * 
	 * @param form  商品絞り込みのフォーム（ワード、カテゴリー、ブランド名）
	 * @param model モデル
	 * @return 商品一覧表示画面
	 */
	@PostMapping("/select")
	public String select(SelectItemForm form, Model model) {
		List<Item> itemList = new ArrayList<>();
		itemList = showSelectedItemListService.showSelectedItemList(form);
		List<Category> parentCategoryList = selectCategoryService.viewParentCategory();
		Category childCategory = new Category();
		Category grandChildCategory = new Category();
		if (form.getChildCategoryId() != 0) {
			childCategory = selectCategoryService.selectCategoryById(form.getChildCategoryId());
		}
		if (form.getGrandChildCategoryId() != 0) {
			grandChildCategory = selectCategoryService.selectCategoryById(form.getGrandChildCategoryId());
		}
		model.addAttribute("itemList", itemList);
		model.addAttribute("selectItemForm", form);
		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("childCategory", childCategory);
		model.addAttribute("grandChildCategory", grandChildCategory);
		if (itemList.isEmpty()) {
			model.addAttribute("empty", "該当する商品はありません");
		}
		return "list";
	}

	@GetMapping("/sortbycategory")
	public String sortByCategory(Model model, Integer categoryId, Integer hierarchy) {
		SelectItemForm form = new SelectItemForm();
		List<Item> itemList = new ArrayList<>();
		Category firstcategory = new Category();
		firstcategory.setId(categoryId);
		itemList = showSelectedItemListService.sortByCategory(categoryId);
		List<Category> parentCategoryList = selectCategoryService.viewParentCategory();
		List<Category> list = new ArrayList<>();
		list.add(firstcategory);
		Integer newcategoryId = categoryId;

		for (int i = 1; i < hierarchy; i++) {
			Category category = selectCategoryService.searchParentCategory(newcategoryId);
			list.add(category);
			newcategoryId = category.getId();
		}
		Collections.reverse(list);
		Category childCategory = new Category();
		childCategory.setId(0);
		Category grandChildCategory = new Category();
		grandChildCategory.setId(0);
		form.setParentCategoryId(categoryId);
		form.setParentCategoryId(list.get(0).getId());
		if (list.size() > 1) {
			childCategory = selectCategoryService.selectCategoryById(list.get(1).getId());
			form.setChildCategoryId(list.get(1).getId());
		}
		model.addAttribute("childCategory", childCategory);
		if (list.size() > 2) {
			grandChildCategory = selectCategoryService.selectCategoryById(list.get(2).getId());
			form.setGrandChildCategoryId(list.get(2).getId());
		}
		model.addAttribute("grandChildCategory", grandChildCategory);
		model.addAttribute("itemList", itemList);
		model.addAttribute("selectItemForm", form);
		model.addAttribute("parentCategoryList", parentCategoryList);
		return "list";
	}

	@GetMapping("/sortbybrand")
	public String sortByBrand(Model model, String brand) {
		SelectItemForm form = new SelectItemForm();
		form.setBrand(brand);
		List<Item> itemList = new ArrayList<>();
		itemList = showSelectedItemListService.sortByBrand(brand);
		List<Category> parentCategoryList = selectCategoryService.viewParentCategory();
		Category emptyCategory = new Category();
		model.addAttribute("selectItemForm", form);
		model.addAttribute("itemList", itemList);
		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("childCategory", emptyCategory);
		model.addAttribute("grandChildCategory", emptyCategory);
		return "list";
	}
}