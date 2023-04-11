package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SelectItemForm;
import com.example.service.SelectCategoryService;
import com.example.service.ShowCategoryListService;
import com.example.service.ShowSelectedItemListService;

/**
 * 検索フォームに紐づく商品情報リスト表示コントローラー
 * 
 * @author yoshida_yuuta
 *
 */
@Controller
@RequestMapping("/select")
public class ShowSelectedItemListController {
	@Autowired
	private ShowSelectedItemListService showSelectedItemListService;
	@Autowired
	private ShowCategoryListService showCategoryListService;
	@Autowired
	private SelectCategoryService selectCategoryService;


	/**
	 * 商品絞り込みフォームによる商品リスト表示
	 * 
	 * @param form  商品絞り込みのフォーム（ワード、カテゴリー、ブランド名）
	 * @param model モデル
	 * @return 商品一覧表示画面
	 */
	@PostMapping("/run")
	public String showSelectedItemList(SelectItemForm form, Model model) {
		List<Item> itemList = new ArrayList<>();
		itemList = showSelectedItemListService.showSelectedItemList(form);
		List<Category> parentCategoryList = showCategoryListService.viewParentCategory();
		Category childCategory=new Category();
		Category grandChildCategory=new Category();
		if(form.getChildCategoryId()!=null) {
		childCategory= selectCategoryService.selectCategoryById(form.getChildCategoryId());}
		if(form.getGrandChildCategoryId()!=null) {
		grandChildCategory=selectCategoryService.selectCategoryById(form.getGrandChildCategoryId());}
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
	
}
