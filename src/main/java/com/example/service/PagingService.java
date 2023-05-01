package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.form.SelectItemForm;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;

@Service
@Transactional
public class PagingService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * 総ページ検索
	 * 
	 * @return 総ページ数
	 */
	public Integer totalPageCount(SelectItemForm selectItemForm) {
		Category category = new Category();
		if (selectItemForm.getItemName() == null) {
			selectItemForm.setItemName("");
		}
		if (selectItemForm.getBrand() == null) {
			selectItemForm.setBrand("");
		}
		
		if (selectItemForm.getGrandChildCategoryId() != 0) {
			category = categoryRepository.load(selectItemForm.getGrandChildCategoryId());
		} else if (selectItemForm.getChildCategoryId() != 0) {
			category = categoryRepository.load(selectItemForm.getChildCategoryId());
		} else if (selectItemForm.getParentCategoryId() != 0) {
			category = categoryRepository.load(selectItemForm.getParentCategoryId());
		}else {category.setId(0);}
		Integer maxDepth = categoryRepository.findMaxDepth();
		Integer pageCount = itemRepository.countTotalItem(maxDepth, selectItemForm.getItemName(), category, selectItemForm.getBrand()) / 100 + 1;
		return pageCount;
	}

}
