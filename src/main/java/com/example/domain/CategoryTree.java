package com.example.domain;

/**
 * カテゴリーの閉包テーブル
 * 
 * @author yoshdia_yuuta
 *
 */
public class CategoryTree {
	/** 親カテゴリーid */
	private Integer parentId;
	/** 子カテゴリーid */
	private Integer childId;
	/** 階層差 */
	private Integer depth;

	public CategoryTree() {
	}

	public CategoryTree(Integer id, Integer parentId, Integer childId, Integer depth) {
		super();
		this.parentId = parentId;
		this.childId = childId;
		this.depth = depth;
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

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "CategoryTree [  parentId=" + parentId + ", childId=" + childId + ", depth=" + depth + "]";
	}

}
