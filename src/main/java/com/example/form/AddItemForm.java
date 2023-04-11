package com.example.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * 商品追加用フォーム
 * 
 * @author yuuta_000
 *
 */
public class AddItemForm {
	/** 店舗情報 */
	private Integer storeId;
	/** 商品名 */
	@NotBlank(message = "Please enter Item name")
	private String name;
	/** 商品価格 */
	@NotNull(message = "Please enter price")
	private double price;
	/** カテゴリーid */
	@NotNull(message = "Please select a category")
	private Integer categoryId;
	/** ブランド名 */
	private String brand;
	/** 商品状態 */
	@NotNull(message = "Please enter condition")
	private Integer condition;
	/** 商品詳細 */
	@NotBlank(message = "Please enter description")
	private String description;
	/** 販売状況 */
	private Integer shipping;
	/** 商品画像 */
	private MultipartFile itemImage;
	/** 更新者 */
	private String insertUser;

	public AddItemForm() {
	}

	public AddItemForm(Integer storeId, String name, double price, Integer categoryId, String brand, Integer condition,
			String description, Integer shipping, MultipartFile itemImage, String insertUser) {
		super();
		this.storeId = storeId;
		this.name = name;
		this.price = price;
		this.categoryId = categoryId;
		this.brand = brand;
		this.condition = condition;
		this.description = description;
		this.shipping = shipping;
		this.itemImage = itemImage;
		this.insertUser = insertUser;
	}

	@Override
	public String toString() {
		return "AddItemForm [storeId=" + storeId + ", name=" + name + ", price=" + price + ", categoryId=" + categoryId
				+ ", brand=" + brand + ", condition=" + condition + ", description=" + description + ", shipping="
				+ shipping + ", itemImage=" + itemImage + ", insertUser=" + insertUser + "]";
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

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getCondition() {
		return condition;
	}

	public void setCondition(Integer condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getShipping() {
		return shipping;
	}

	public void setShipping(Integer shipping) {
		this.shipping = shipping;
	}

	public MultipartFile getItemImage() {
		return itemImage;
	}

	public void setItemImage(MultipartFile itemImage) {
		this.itemImage = itemImage;
	}

	public String getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}

}
