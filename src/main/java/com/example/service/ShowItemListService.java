package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
import com.example.form.SelectItemForm;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;

/**
 * 全件検索のサービスクラス
 * 
 * @author yuuta_yoshida
 *
 */
@Service
@Transactional
public class ShowItemListService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * ページ内全件検索
	 * 
	 * @return 商品情報全件
	 */
	public Optional<List<Item>> showItemList(SelectItemForm selectItemForm,Integer page, Integer totalPage) {
		Category category = new Category();
		if (page > totalPage) {
			page = totalPage;
		}
		if (selectItemForm.getItemName() == null) {
			selectItemForm.setItemName("");
		}
		if (selectItemForm.getBrand() == null) {
			selectItemForm.setBrand("");
		}
		if(selectItemForm.getSort() == null) {
			selectItemForm.setSort("") ;
		}
		
		if (selectItemForm.getGrandChildCategoryId() != 0) {
			category = categoryRepository.load(selectItemForm.getGrandChildCategoryId());
		} else if (selectItemForm.getChildCategoryId() != 0) {
			category = categoryRepository.load(selectItemForm.getChildCategoryId());
		} else if (selectItemForm.getParentCategoryId() != 0) {
			category = categoryRepository.load(selectItemForm.getParentCategoryId());
		}else {category.setId(0);}
		
		Integer maxDepth = categoryRepository.findMaxDepth();
		Optional<List<Item>> itemList = itemRepository.findAllItem(maxDepth, page,selectItemForm.getItemName(), category, selectItemForm.getBrand(),selectItemForm.getSort());
		return itemList;
	}

	
	/**
	 * 商品カテゴリー検索
	 * @param selectedCategory
	 * @return
	 */
	public Optional<List<Item>> sortByCategory(Integer selectedCategory) {
		Integer maxDepth = categoryRepository.findMaxDepth(); 
		Category category = categoryRepository.load(selectedCategory);
		Optional<List<Item>> selectedItemList = itemRepository.findAllItem(maxDepth, 1,"", category,"","");
		return selectedItemList;
	}
	

	/**
	 * 商品ブランド検索
	 * @param brand
	 * @return
	 */
	public Optional<List<Item>> sortByBrand(String brand) {
		Integer maxDepth = categoryRepository.findMaxDepth(); 
		Category category=new Category();
		Optional<List<Item>> selectedItemList = itemRepository.findAllItem(maxDepth, 1,"", category,brand,"");
		return selectedItemList;
	}
}
