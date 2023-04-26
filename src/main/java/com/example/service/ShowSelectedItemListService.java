package com.example.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SelectItemForm;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;

/**
 * 詳細検索のサービスクラス
 * 
 * @author yoshida_yuuta
 *
 */
@Service
@Transactional
public class ShowSelectedItemListService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * 商品詳細検索
	 * 
	 * @param form 検索情報
	 * @return 商品情報リスト
	 */
	public List<Item> showSelectedItemList(SelectItemForm form) {
		Integer maxDepth = categoryRepository.findMaxDepth(); 
		Category category = new Category();
		if (form.getGrandChildCategoryId() != 0) {
			category = categoryRepository.load(form.getGrandChildCategoryId());
		} else if (form.getChildCategoryId() != 0) {
			category = categoryRepository.load(form.getChildCategoryId());
		} else if (form.getParentCategoryId() != 0) {
			category = categoryRepository.load(form.getParentCategoryId());
		}else {category.setId(0);}
		List<Item> selectedItemList = itemRepository.findAllItem(maxDepth, 1, form.getItemName(), category,form.getBrand(), form.getSort());
		return selectedItemList;
	}
	
	public List<Item> sortByCategory(Integer selectedCategory) {
		Integer maxDepth = categoryRepository.findMaxDepth(); 
		Category category = categoryRepository.load(selectedCategory);
		List<Item> selectedItemList = itemRepository.findAllItem(maxDepth, 1,"", category,"","");
		return selectedItemList;
	}
	
	public List<Item> sortByBrand(String brand) {
		Integer maxDepth = categoryRepository.findMaxDepth(); 
		Category category=new Category();
		List<Item> selectedItemList = itemRepository.findAllItem(maxDepth, 1,"", category,brand,"");
		return selectedItemList;
	}
}
