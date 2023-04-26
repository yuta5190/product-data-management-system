package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
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
	public Integer totalPageCount(String search, Integer parentCategory, Integer childCategory,
			Integer grandChildCategory, String brand) {
		Category category = new Category();
		if (grandChildCategory != 0) {
			category = categoryRepository.load(grandChildCategory);
		} else if (childCategory != 0) {
			category = categoryRepository.load(childCategory);
		} else if (parentCategory != 0) {
			category = categoryRepository.load(parentCategory);
		}else {category.setId(0);}
		Integer maxDepth = categoryRepository.findMaxDepth();
		Integer pageCount = itemRepository.countTotalItem(maxDepth, search, category, brand) / 100 + 1;
		return pageCount;
	}

}
