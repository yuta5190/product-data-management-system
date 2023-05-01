package com.example.form;

/**
 * 商品絞り込み用フォーム
 * 
 * @author yoshida_yuuta
 *
 */
public class SelectItemForm {
	/** 検索ワード */
	private String itemName="";
	/** 大カテゴリー */
	private Integer parentCategoryId=0;
	/** 中カテゴリー */
	private Integer childCategoryId=0;
	/** 小カテゴリー */
	private Integer grandChildCategoryId=0;
	/** ブランド名 */
	private String brand="";
	/** 並び順 */
	private String sort="";

	public SelectItemForm() {
	}

	public SelectItemForm(String itemName, Integer parentCategoryId, Integer childCategoryId,
			Integer grandChildCategoryId, String brand, String sort) {
		super();
		this.itemName = itemName;
		this.parentCategoryId = parentCategoryId;
		this.childCategoryId = childCategoryId;
		this.grandChildCategoryId = grandChildCategoryId;
		this.brand = brand;
		this.sort = sort;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Integer getParentCategoryId() {
		return parentCategoryId;
	}

	public void setParentCategoryId(Integer parentCategoryId) {
		this.parentCategoryId = parentCategoryId;
	}

	public Integer getChildCategoryId() {
		return childCategoryId;
	}

	public void setChildCategoryId(Integer childCategoryId) {
		this.childCategoryId = childCategoryId;
	}

	public Integer getGrandChildCategoryId() {
		return grandChildCategoryId;
	}

	public void setGrandChildCategoryId(Integer grandChildCategoryId) {
		this.grandChildCategoryId = grandChildCategoryId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String toString() {
		return "SelectItemForm [itemName=" + itemName + ", parentCategoryId=" + parentCategoryId + ", childCategoryId="
				+ childCategoryId + ", grandChildCategoryId=" + grandChildCategoryId + ", brand=" + brand + ", sort="
				+ sort + "]";
	}

}