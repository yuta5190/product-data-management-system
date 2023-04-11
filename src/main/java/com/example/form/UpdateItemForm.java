package com.example.form;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Category;

/**
 * 商品更新用フォーム
 * 
 * @author yoshida_yuuta
 *
 */
public class UpdateItemForm {
	/** 商品id */
	private Integer id;
	/** 店舗名 */
	private Integer storeId;
	/** 商品名 */
	private String name;
	/** 商品状態 */
	private Integer condition;
	/** 商品カテゴリー */
	private Integer category;
	/** ブランド名 */
	private String brand;
	/** 価格 */
	private double price;
	/** 販売状況 */
	private Integer shipping;
	/** 詳細 */
	private String description;
	/** 商品画像 */
	private MultipartFile itemImage;
	/** 商品画像名 */
	private String itemImageName;
	/** カテゴリーリスト */
	private List<Category> categoryDetail;
	private Integer parentCategory;
	private Integer childCategory;
	private Integer grandChildCategory;

	public UpdateItemForm() {
	}

	public UpdateItemForm(Integer id, Integer storeId, String name, Integer condition, Integer category, String brand,
			double price, Integer shipping, String description, MultipartFile itemImage, String itemImageName,
			List<Category> categoryDetail, Integer parentCategory, Integer childCategory, Integer grandChildCategory) {
		super();
		this.id = id;
		this.storeId = storeId;
		this.name = name;
		this.condition = condition;
		this.category = category;
		this.brand = brand;
		this.price = price;
		this.shipping = shipping;
		this.description = description;
		this.itemImage = itemImage;
		this.itemImageName = itemImageName;
		this.categoryDetail = categoryDetail;
		this.parentCategory = parentCategory;
		this.childCategory = childCategory;
		this.grandChildCategory = grandChildCategory;
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

	public MultipartFile getItemImage() {
		return itemImage;
	}

	public void setItemImage(MultipartFile itemImage) {
		this.itemImage = itemImage;
	}

	public String getItemImageName() {
		return itemImageName;
	}

	public void setItemImageName(String itemImageName) {
		this.itemImageName = itemImageName;
	}

	public List<Category> getCategoryDetail() {
		return categoryDetail;
	}

	public void setCategoryDetail(List<Category> categoryDetail) {
		this.categoryDetail = categoryDetail;
	}

	public Integer getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Integer parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Integer getChildCategory() {
		return childCategory;
	}

	public void setChildCategory(Integer childCategory) {
		this.childCategory = childCategory;
	}

	public Integer getGrandChildCategory() {
		return grandChildCategory;
	}

	public void setGrandChildCategory(Integer grandChildCategory) {
		this.grandChildCategory = grandChildCategory;
	}

	@Override
	public String toString() {
		return "UpdateItemForm [id=" + id + ", storeId=" + storeId + ", name=" + name + ", condition=" + condition
				+ ", category=" + category + ", brand=" + brand + ", price=" + price + ", shipping=" + shipping
				+ ", description=" + description + ", itemImage=" + itemImage + ", itemImageName=" + itemImageName
				+ ", categoryDetail=" + categoryDetail + ", parentCategory=" + parentCategory + ", childCategory="
				+ childCategory + ", grandChildCategory=" + grandChildCategory + "]";
	}



}
