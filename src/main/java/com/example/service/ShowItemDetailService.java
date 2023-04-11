package com.example.service;

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
public class ShowItemDetailService {
	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品情報検索
	 * 
	 * @param id 検索する商品id
	 * @return 商品情報
	 */
	public Item showItemDetail(Integer id) {
		Item item = itemRepository.load(id);
		return item;
	}
}
