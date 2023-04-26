package com.example.domain;
/**
 * カテゴリー
 * 
 * @author yoshida_yuuta
 *
 */
public class Category {
	/** カテゴリーid */
	private Integer id;
	/** 大カテゴリーid */
	private Integer parentId;
	/** 小カテゴリーid */
	private Integer childId;
	/** カテゴリー名 */
	private String categoryName;
	/** カテゴリーの階層 大:0 中:1 小:2 */
	private Integer hierarchy;
	/** カテゴリまでのルート 大/中/小 */
	private String categoryRoot;

	public Category() {
	}

	public Category(Integer id, Integer parentId, Integer childId, String categoryName, Integer hierarchy,
			String categoryRoot) {
		super();
		this.id = id;
		this.parentId = parentId;
		this.childId = childId;
		this.categoryName = categoryName;
		this.hierarchy = hierarchy;
		this.categoryRoot = categoryRoot;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Integer getHierarchy() {
		return hierarchy;
	}

	public void setHierarchy(Integer hierarchy) {
		this.hierarchy = hierarchy;
	}

	public String getCategoryRoot() {
		return categoryRoot;
	}

	public void setCategoryRoot(String categoryRoot) {
		this.categoryRoot = categoryRoot;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", parentId=" + parentId + ", childId=" + childId + ", categoryName="
				+ categoryName + ", hierarchy=" + hierarchy + ", categoryRoot=" + categoryRoot + "]";
	}

}
