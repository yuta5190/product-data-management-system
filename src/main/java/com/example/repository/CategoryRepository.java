package com.example.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.CategoryTree;
@Mapper
@Repository
@Transactional
public interface CategoryRepository {
	
	/**
	 * 大カテゴリー全件検索
	 * @return
	 */
	List<Category> findAllParentCategory();
	
	/**
	 * カテゴリーid検索
	 * @return
	 */
	Category load(@Param("categoryId") Integer categoryId);
	
	/**
	 * 親カテゴリー検索
	 * @param categoryId
	 * @return
	 */
	Category findParentCategoryByChildId(@Param ("categoryId") Integer categoryId);
	
	/**
	 * 子カテゴリー検索
	 * @param parentId
	 * @return
	 */
	List<CategoryTree> findChildCategoryList(@Param("parentId") Integer parentId, @Param("depth") Integer depth);
	
	/**
	 * カテゴリーの最大階層検索
	 * @return
	 */
	Integer findMaxDepth();
}
