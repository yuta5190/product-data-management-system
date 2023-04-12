package com.example.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;
import com.example.domain.CategoryTree;

/**
 * カテゴリーリポジトリ－
 * 
 * @author yoshida_yuuta
 *
 */

@Repository
public class CategoryRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 親カテゴリー用のマッパ－クラス
	 */
	private static final RowMapper<Category> PARENTCATEGORY_ROW_MAPPER = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setCategoryName(rs.getString("category_name").toString());
		return category;
	};

	/**
	 * カテゴリーリスト作成用のマッパークラス
	 */
	private static final RowMapper<CategoryTree> CATEGORYLIST_ROW_MAPPER = (rs, i) -> {
		CategoryTree categoryTree = new CategoryTree();
		categoryTree.setChildId(rs.getInt("child_id"));
		return categoryTree;
	};

	/**
	 * 中カテゴリー用のマッパークラス
	 */
	private static final RowMapper<Category> CHILDCATEGORY_ROW_MAPPER = (rs, i) -> {
		Category category = new Category();
		category.setId(rs.getInt("id"));
		category.setCategoryName(rs.getString("category_name"));
		category.setHierarchy(rs.getInt("hierarchy"));
		return category;
	};

	/**
	 * カテゴリーの最大階層検索用マッパークラス
	 */
	private static final RowMapper<Integer> MAXNUM_ROW_MAPPER = (rs, i) -> {
		Integer maxNum = rs.getInt("max");
		return maxNum;
	};

	/**
	 * カテゴリー名取得用のマッパークラス
	 */
	/*
	 * private static final RowMapper<String> CATEGORYNAME_ROW_MAPPER = (rs, i) -> {
	 * String categoryName = rs.getString("category_name"); return categoryName; };
	 */

	/**
	 * 大カテゴリー全件検索
	 * 
	 * @return 全ての大カテゴリー
	 */
	public List<Category> findAllParentCategory() {
		String sql = "SELECT id,category_name FROM categorys WHERE hierarchy = 0 ORDER BY id";
		List<Category> categoryList = template.query(sql, PARENTCATEGORY_ROW_MAPPER);
		return categoryList;
	}

	/**
	 * カテゴリーid検索
	 * 
	 * @param id カテゴリーid
	 * @return idに紐づくカテゴリー情報（Categoryオブジェクト）
	 */
	public Category findCategoryById(Integer id) {
		String sql = "SELECT id,category_name,hierarchy FROM categorys WHERE id=:id  ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Category> categoryList = template.query(sql, param, CHILDCATEGORY_ROW_MAPPER);
		Category category = categoryList.get(0);
		return category;
	}
	
	/**
	 * 親カテゴリー検索
	 * 
	 * @param id カテゴリーid
	 * @return idに紐づくカテゴリー情報（Categoryオブジェクト）
	 */
	public Category searchParentCategory(Integer categoryId) {
		String sql = "SELECT c.id AS id,c.category_name AS category_name FROM categorys AS p LEFT JOIN categorytree AS ptree ON p.id = ptree.child_id LEFT join categorys AS c ON ptree.parent_id=c.id  WHERE p.id=:categoryId  ORDER BY id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("categoryId", categoryId);
		List<Category> categoryList = template.query(sql, param, PARENTCATEGORY_ROW_MAPPER);
		Category category = categoryList.get(0);
		return category;
	}


	/**
	 * 子カテゴリー検索
	 * 
	 * @param id        親カテゴリーid
	 * @param hierarchy 欲しいカテゴリーの関係設定 親子:１ 親孫:2
	 * @return 親カテゴリー情報
	 */
	public List<CategoryTree> findNextCategoryList(Integer id, Integer hierarchy) {
		String sql = "SELECT child_id FROM categorytree WHERE parent_id=:id AND depth =:depth ORDER BY child_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id).addValue("depth", hierarchy);
		List<CategoryTree> categoryList = template.query(sql, param, CATEGORYLIST_ROW_MAPPER);
		return categoryList;
	}

	/**
	 * カテゴリーの最大階層検索
	 * 
	 * @return 現在のカテゴリーの中の最大階層
	 */
	public Integer findMaxDepth() {
		String sql = "SELECT MAX(hierarchy) FROM categorys;";
		List<Integer> maxNum = template.query(sql, MAXNUM_ROW_MAPPER);
		return maxNum.get(0);
	}
	
}
