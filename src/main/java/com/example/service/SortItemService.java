package com.example.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;

/**
 * 商品詳細情報表示のサービス
 * 
 * @author yoshida_yuuta
 *
 */
@Service
@Transactional
public class SortItemService {
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品情報検索
	 * 
	 * @param id 検索する商品id
	 * @return 商品情報
	 */
	public Optional<List<Item>> showItemDetail(Integer id) {
		Optional<List<Item>> item = itemRepository.load(id);
		return item;
	}
}
