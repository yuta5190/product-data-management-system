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
 * カテゴリーリストを返すサービスクラス
 * @author yoshidayuuta
 *
 */
@Service
@Transactional
public class ShowCategoryListService {

	@Autowired
	private CategoryRepository categoryrepository;

	/**
	 * 大カテゴリーの表示
	 * @return　大カテゴリーリスト
	 */
	public List<Category> viewParentCategory() {
		
		List<Category> parentCategoryList = categoryrepository.findAllParentCategory();
		return parentCategoryList;
	}

	/**
	 * 中カテゴリーの表示
	 * @param parentId　選択した大カテゴリー
	 * @return　中カテゴリーリスト
	 */
	public List<Category> viewCategory(int id,int hierarchy) {
		List<CategoryTree> categoryTrees= categoryrepository.findNextCategoryList(id, hierarchy);
		List<Category> categoryList=new ArrayList<>();
	    for(CategoryTree categoryTree:categoryTrees) {
	    categoryList.add(categoryrepository.findCategoryById(categoryTree.getChildId()));	
	    }
		return categoryList;
	}


}
