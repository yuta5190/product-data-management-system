
package com.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Item;
import com.example.form.AddItemForm;
import com.example.repository.ItemRepository;

/**
 * 商品追加サービスクラス
 * 
 * @author yuuta_yoshida
 *
 */
@Service
@Transactional
public class ResisterItemService {
	@Autowired
	public ItemRepository itemRepository;

	/**
	 * フォーム内容をinsertのリポジトリに送る
	 * 
	 * @param form 商品追加フォーム
	 * @throws IOException
	 */
	public void insertItem(AddItemForm form) throws IOException {
		/** form内容をItemオブジェクトに反映 */
		Item item = this.parseFormToItem(form);
		itemRepository.insertItem(item);
	}

	/**
	 * @param form 商品追加フォーム
	 * @return form内容を移したItemオブジェクト
	 * @throws IOException
	 */
	public Item parseFormToItem(AddItemForm form) throws IOException {
		Item item = new Item();
		if (form.getStoreId() == null) {
			item.setStoreId(0);
		} else {
			item.setStoreId(form.getStoreId());
		}
		item.setName(form.getName());
		item.setCondition(form.getCondition());
		item.setCategory(form.getCategoryId());
		item.setBrand(form.getBrand());
		item.setPrice(form.getPrice());
		item.setShipping(0);
		item.setDescription(form.getDescription());
		/** saveImage:画像ファイルを渡してファイル名を返す */
		String fileName = this.saveImage(form.getItemImage());
		item.setItemImage(fileName);
		item.setDescription(form.getDescription());
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		item.setInsertDate(timestamp);
		if (form.getInsertUser() == null) {
			item.setInsertUser(null);
		} else {
			item.setInsertUser(form.getInsertUser());
		}
		return item;
	}

	/**
	 * @param itemImage 画像ファイル
	 * @return 画像名
	 * @throws IOException
	 */
	public String saveImage(MultipartFile itemImage) throws IOException {
		String uploadDir = "";//登録したいフォルダーパス
		if(itemImage.isEmpty()) {
			return "";}
	
		Path uploadPath = Paths.get(uploadDir);
		if (!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		String contentType = itemImage.getContentType();
		String FileName = null;
		if (contentType.equals("image/jpeg")) {
			FileName = UUID.randomUUID().toString() + ".jpeg";
		}
		if (contentType.equals("image/png")) {
			FileName = UUID.randomUUID().toString() + ".png";
		}

		// ファイルをアップロード先に保存する
		Path filePath = uploadPath.resolve(FileName);
		Files.copy(itemImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		return FileName;
	}
}