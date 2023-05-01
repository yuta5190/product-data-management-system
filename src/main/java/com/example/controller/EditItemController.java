package com.example.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.UpdateItemForm;
import com.example.service.SelectCategoryService;
import com.example.service.SortItemService;
import com.example.service.EditItemService;

/**
 * 商品情報更新のコントローラー
 * 
 * @author yoshida_yuuta
 *
 */
@Controller
@RequestMapping("/edit")
public class EditItemController {
	@Autowired
	public SortItemService showItemDetail;
	@Autowired
	public EditItemService editItemService;
	@Autowired
	public SelectCategoryService selectCategory;

	/**
	 * 商品情報更新画面表示
	 * 
	 * @param model モデル
	 * @param id    更新したい商品のid
	 * @param form  更新内容入力フォーム
	 * @return 商品更新画面
	 */
	@PostMapping("")
	public String index(Model model, Integer id, UpdateItemForm form) {
		Item item = showItemDetail.showItemDetail(id);
		form.setId(item.getId());
		form.setName(item.getName());
		form.setStoreId(item.getStoreId());
		form.setCondition(item.getCondition());
		form.setBrand(item.getBrand());
		form.setPrice(item.getPrice());
		form.setShipping(item.getShipping());
		form.setDescription(item.getDescription());
		form.setItemImageName(item.getItemImage());
		if (item.getItemImage() == null || item.getItemImage().equals("")) {
			form.setItemImageName("noimage-760x460.png");
		}
		List<Category> parentCategoryList = selectCategory.selectParentCategory();
		model.addAttribute("parentCategoryList", parentCategoryList);
		Category parentCategory =item.getCategoryDetail().get(0);
		Category childCategory =item.getCategoryDetail().get(1);
		Category grandChildCategory =item.getCategoryDetail().get(2);
		form.setParentCategory(parentCategory.getId());
		form.setChildCategory(childCategory.getId());
		form.setGrandChildCategory(grandChildCategory.getId());
		model.addAttribute("id", id);
		model.addAttribute("updateItemForm",form);
		model.addAttribute("childCategory", childCategory);
		model.addAttribute("grandChildCategory", grandChildCategory);
		return "edit";
	}

	/**
	 * 商品情報更新
	 * @param form　更新フォーム
	 * @return　商品詳細画面
	 * @throws IOException
	 */
	@PostMapping("/edititem")
	public String editItem(@ModelAttribute UpdateItemForm form) throws IOException {
		editItemService.updateItem(form);
		return "redirect:/showitemdetail?id=" + form.getId();
	}

}
