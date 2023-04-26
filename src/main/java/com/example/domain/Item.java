package com.example.domain;

import java.sql.Timestamp;
import java.util.List;

/**
 * 商品情報
 * 
 * @author yoshida_yuutaF
 *
 */
public class Item {
	/** 商品id */
	private Integer id;
	/** 店舗id */
	private Integer storeId;
	/** 商品名 */
	private String name;
	/** 商品状態 */
	private Integer condition;
	/** カテゴリーid */
	private Integer category;
	/** カテゴリーの階層 */
	private Integer hierarchy;
	/** ブランド名 */
	private String brand;
	/** 価格 */
	private double price;
	/**価格表示時に.0が消去されるためStringに対比する用*/
	private String stringPrice;
	/** 販売状態 */
	private Integer shipping;
	/** 詳細 */
	private String description;
	/** 商品画像 */
	private String itemImage;
	/** 更新日 */
	private Timestamp insertDate;
	/** 更新者 */
	private Integer insertUser;
	/** カテゴリー情報 */
	private List<Category> categoryDetail;

	public Item() {
	}

	public Item(Integer id, Integer storeId, String name, Integer condition, Integer category, Integer hierarchy,
			String brand, double price, String stringPrice, Integer shipping, String description, String itemImage,
			Timestamp insertDate, Integer insertUser, List<Category> categoryDetail) {
		super();
		this.id = id;
		this.storeId = storeId;
		this.name = name;
		this.condition = condition;
		this.category = category;
		this.hierarchy = hierarchy;
		this.brand = brand;
		this.price = price;
		this.stringPrice = stringPrice;
		this.shipping = shipping;
		this.description = description;
		this.itemImage = itemImage;
		this.insertDate = insertDate;
		this.insertUser = insertUser;
		this.categoryDetail = categoryDetail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public Integer getCategory() {
		return category;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public Integer getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Integer hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStringPrice() {
		return stringPrice;
	}

	public void setStringPrice(String stringPrice) {
		this.stringPrice = stringPrice;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getItemImage() {
		return itemImage;
	}

	public void setItemImage(String itemImage) {
		this.itemImage = itemImage;
	}

	public Timestamp getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public Integer getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(Integer insertUser) {
		this.insertUser = insertUser;
	}

	public List<Category> getCategoryDetail() {
		return categoryDetail;
	}

	public void setCategoryDetail(List<Category> categoryDetail) {
		this.categoryDetail = categoryDetail;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", storeId=" + storeId + ", name=" + name + ", condition=" + condition + ", category="
				+ category + ", hierarchy=" + hierarchy + ", brand=" + brand + ", price=" + price + ", stringPrice="
				+ stringPrice + ", shipping=" + shipping + ", description=" + description + ", itemImage=" + itemImage
				+ ", insertDate=" + insertDate + ", insertUser=" + insertUser + ", categoryDetail=" + categoryDetail
				+ "]";
	}

	

}