package com.example.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SelectItemForm;
import com.example.service.PagingService;
import com.example.service.SelectCategoryService;
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
	private ShowItemListService showItemListService;
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
		List<Category> parentCategoryList = selectCategoryService.selectParentCategory();
		Integer totalPage = pagingService.totalPageCount(form);
		model.addAttribute("parentCategoryList", parentCategoryList);
		
		Optional<List<Item>> itemList = showItemListService.showItemList(form,1,totalPage);
		model.addAttribute("itemList",itemList.get());
		
		Category emptyCategoryList = new Category();
		model.addAttribute("childCategory", emptyCategoryList);
		model.addAttribute("grandChildCategory", emptyCategoryList);
		return "list";
	}

	/**
	 * １ページ目表示、アイテム数による総ページ計算
	 * 
	 * @return １ページ目表示に関わるJavaScript
	 */
	@GetMapping("/showfirstitemlist")
	@ResponseBody
	public Map<String, Object> showFirstItemList(@ModelAttribute SelectItemForm form) {
		
		Map<String, Object> firstPageData = new HashMap<>();
		Integer totalPage = pagingService.totalPageCount(form);
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
	public Map<String, Object> paging(Integer page, @ModelAttribute SelectItemForm form, Integer totalPage) {
		Objects.requireNonNull(page, "/showitemlist/paging: page must not be null");
		Objects.requireNonNull(totalPage, "/showitemlist/paging: totalPage must not be null");		
		Optional<List<Item>> itemList = showItemListService.showItemList( form,page, totalPage);
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
	@PostMapping("/sort")
	public String Sort(SelectItemForm form, Model model) {
		
		List<Category> parentCategoryList = selectCategoryService.selectParentCategory();
		Category childCategory = new Category();
		Category grandChildCategory = new Category();
		if (form.getChildCategoryId() != 0) {
			childCategory = selectCategoryService.selectCategoryById(form.getChildCategoryId());
		}
		if (form.getGrandChildCategoryId() != 0) {
			grandChildCategory = selectCategoryService.selectCategoryById(form.getGrandChildCategoryId());
		}
		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("childCategory", childCategory);
		model.addAttribute("grandChildCategory", grandChildCategory);
		
		
		Integer totalPage = pagingService.totalPageCount(form);
		Optional<List<Item>> itemList = showItemListService.showItemList(form,1,totalPage);
		model.addAttribute("itemList", itemList.isPresent() ? itemList.get() : new ArrayList<>());
		
		model.addAttribute("selectItemForm", form);
	
		return "list";
	}

	/**
	 * カテゴリー検索
	 * @param model　モデル
	 * @param categoryId　カテゴリーId
	 * @param hierarchy　カテゴリーの深さ情報
	 * @return　商品一覧画面表示
	 */
	@GetMapping("/sortbycategory")
	public String sortByCategory(Model model, Integer categoryId, Integer hierarchy) {
		Objects.requireNonNull(categoryId, "/showitemlist/sortbycategory: categoryId must not be null");
		Objects.requireNonNull(hierarchy, "/showitemlist/sortbycategory: hierarchy must not be null");
		SelectItemForm form = new SelectItemForm();
	
		/**検索フォームのカテゴリータブ情報*/
		List<Category> parentCategoryList = selectCategoryService.selectParentCategory();
		model.addAttribute("parentCategoryList", parentCategoryList);
		
		/**検索情報を引き継ぐ*/
		//大カテゴリー
		List<Category> categoryList = new ArrayList<>();
		Category sortcategory = new Category(categoryId);
		categoryList.add(sortcategory);
		
		
		Integer newCategoryId = categoryId;
		for (int i = 1; i < hierarchy; i++) {
			Category category = selectCategoryService.searchParentCategory(newCategoryId);
			categoryList.add(category);
			newCategoryId = category.getId();
		}
		Collections.reverse(categoryList);
		
		//大カテゴリー
		form.setParentCategoryId( categoryList.get(0).getId() );
		
		//中カテゴリー
		Category childCategory = (categoryList.size() > 1) ? selectCategoryService.selectCategoryById(categoryList.get(1).getId()) :new Category(0);
		form.setChildCategoryId(childCategory.getId());
		model.addAttribute("childCategory", childCategory);
		
		//小カテゴリー
		Category grandChildCategory = (categoryList.size() > 2) ? selectCategoryService.selectCategoryById(categoryList.get(2).getId()) :new Category(0);
		form.setGrandChildCategoryId(grandChildCategory.getId());
		model.addAttribute("grandChildCategory", grandChildCategory);
	
	
		/**アイテム情報取得*/
		Optional<List<Item>> itemList = showItemListService.sortByCategory(categoryId);
		model.addAttribute("itemList", itemList.isPresent() ? itemList.get() : new ArrayList<>());
		model.addAttribute("selectItemForm", form);
	
		return "list";
	}

	/**
	 * ブランド情報検索
	 * @param model　モデル
	 * @param brand　ブランドあいまい検索ワード
	 * @return　商品情報表示画面
	 */
	@GetMapping("/sortbybrand")
	public String sortByBrand(Model model, String brand) {
		Objects.requireNonNull(brand, "/showitemlist/sortbybrand: brand must not be null");
		SelectItemForm form = new SelectItemForm();
		form.setBrand(brand);
		
		Optional<List<Item>> itemList = showItemListService.sortByBrand(brand);
		List<Category> parentCategoryList = selectCategoryService.selectParentCategory();
		Category emptyCategory = new Category();
		model.addAttribute("selectItemForm", form);
		model.addAttribute("itemList", itemList.isPresent() ? itemList.get() : new ArrayList<>());
		model.addAttribute("parentCategoryList", parentCategoryList);
		model.addAttribute("childCategory", emptyCategory);
		model.addAttribute("grandChildCategory", emptyCategory);
		return "list";
	}
}