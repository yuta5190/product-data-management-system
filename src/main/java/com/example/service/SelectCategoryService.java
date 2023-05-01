package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Category;
import com.example.domain.CategoryTree;
import com.example.repository.CategoryRepository;

/**
 * カテゴリー検索サービス
 * @author yoshida_yuuta
 *
 */
@Service
@Transactional
public class SelectCategoryService {
	@Autowired
	private CategoryRepository categoryRepository;
	

	public Category selectCategoryById(Integer id) {
		Category category = categoryRepository.load(id);
		return category;
	}
	public Category searchParentCategory(Integer id) {
		Category category = categoryRepository.findParentCategoryByChildId(id);
		return category;
	}
	/**
	 * 大カテゴリーの表示
	 * @return　大カテゴリーリスト
	 */
	public List<Category> selectParentCategory() {
		
		List<Category> parentCategoryList = categoryRepository.findAllParentCategory();
		return parentCategoryList;
	}

	/**
	 * 中カテゴリーの表示
	 * @param parentId　選択した大カテゴリー
	 * @return　中カテゴリーリスト
	 */
	public List<Category> selectCategory(int id,int hierarchy) {
		List<CategoryTree> categoryTrees= categoryRepository.findChildCategoryList(id, hierarchy);
		List<Category> categoryList=new ArrayList<>();
	    for(CategoryTree categoryTree:categoryTrees) {
	    categoryList.add(categoryRepository.load(categoryTree.getChildId()));	
	    }
		return categoryList;
	}
}
