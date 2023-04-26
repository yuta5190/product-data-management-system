package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.Item;
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
	public List<Item> showItemList(Integer page, String search, Integer parentCategory, Integer childCategory,
			Integer grandChildCategory, String brand, Integer totalPage,String orderBy) {
		if (page > totalPage) {
			page = totalPage;
		}
		Category category = new Category();
		if (grandChildCategory != 0 ) {
			category = categoryRepository.load(grandChildCategory);
		} else if (childCategory != 0) {
			category = categoryRepository.load(childCategory);
		} else if (parentCategory != 0) {
			category = categoryRepository.load(parentCategory);
		}else {category.setId(0);}
		Integer maxDepth = categoryRepository.findMaxDepth();
		List<Item> itemList = itemRepository.findAllItem(maxDepth, page,search, category, brand,orderBy);
		return itemList;
	}

	
}
