package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.CategoryRepository;
import com.example.repository.ItemRepository;

/**
 * @author yoshida_yuuta
 *  
 *  カテゴリーをクリックした際にItemを絞り込むサービス
 */
@Service
@Transactional
public class SortByCategoryService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private CategoryRepository categoryRepository;
public List<Item> sortByCategory(Integer id){
Integer maxDepth = categoryRepository.findMaxDepth(); 
List<Item> itemList= itemRepository.findAllItem(maxDepth, 1, null, null, null, null);
return itemList;
}
}
