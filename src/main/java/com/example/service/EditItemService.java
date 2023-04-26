package com.example.service;

import java.io.IOException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.domain.Item;
import com.example.form.UpdateItemForm;
import com.example.repository.ItemRepository;

/**
 * 商品情報更新用サービス
 * 
 * @author yoshida_yuuta
 */

@Service
@Transactional
public class EditItemService {
	@Autowired
	private ItemRepository itemRepository;
	@Autowired
	private ResisterItemService resisterItemService;
	/**
	 * 商品情報更新
	 * @param item　商品更新情報
	 * @throws IOException 
	 */
	public void updateItem(@ModelAttribute UpdateItemForm form) throws IOException {
		Item item=new Item();
		item.setId(form.getId());
		item.setName(form.getName());
		item.setCondition(form.getCondition());
		item.setCategory(form.getGrandChildCategory());
		item.setBrand(form.getBrand());
		item.setPrice(form.getPrice());
		/**仮で０入れる*/
		//item.setShipping(form.getShipping());
		item.setShipping(0);
		item.setDescription(form.getDescription());
		String fileName = resisterItemService.saveImage(form.getItemImage());
		item.setItemImage(fileName);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		item.setInsertDate(timestamp);
		itemRepository.update(item);
	}
}
