package com.example.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;
import com.example.domain.Item;

/**
 * 商品用リポジトリ－
 * 
 * @author yoshida_yuuta
 *
 */
@Repository
public class ItemRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;
	public static Integer maxCategoryDepth;
	public static Integer categoryCount;

	/**
	 * 商品＋カテゴリーテーブル結合後用のマッパークラス
	 */
	private static final ResultSetExtractor<Optional<List<Item>>> ALLITEM_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Item> itemList=new ArrayList<>();
		while (rs.next()) {
		Item item = new Item();
		List<Category> categoryList = new ArrayList<>();
		item.setId(rs.getInt("id"));
		item.setStoreId(rs.getInt("store_id"));
		item.setName(rs.getString("name"));
		item.setCondition(rs.getInt("condition"));
		item.setCategory(rs.getInt("category"));
		item.setBrand(rs.getString("brand"));
		item.setPrice(rs.getDouble("price"));
		Double price = rs.getDouble("price");
		item.setStringPrice(price.toString());
		item.setShipping(rs.getInt("shipping"));
		item.setDescription(rs.getString("description"));
		item.setItemImage(rs.getString("item_image"));
		item.setInsertDate(rs.getTimestamp("insert_date"));
		item.setInsertUser(rs.getInt("insert_user"));
		for (int j = rs.getInt("hierarchy"); j >=0 ; j--) {
			Category category = new Category();
			category.setCategoryName(rs.getString("category_name_" + j));
			category.setId(rs.getInt("category_id_" + j));
			categoryList.add(category);
		}
		item.setCategoryDetail(categoryList);
		itemList.add(item);
		}
		return Optional.ofNullable(itemList.isEmpty() ? null : itemList);}
	;

	private static final RowMapper<Integer> COUNT_ROW_MAPPER = (rs, i) -> {
		Integer count = rs.getInt("count");
		return count;
	};

	/**
	 * 商品追加
	 * 
	 * @param item 追加商品情報
	 */
	public void insertItem(Item item) {
		String sql = "INSERT INTO items (store_id,name,condition,category,brand,price,shipping,description,item_image,insert_date,insert_user) Values (:storeId,:name,:condition,:category,:brand,:price,:shipping,:description,:itemImage,:insertDate,:insertUser) ;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}

	/**
	 * ページに紐づく全件検索(1pに100件)
	 * 
	 * @param maxDepth 最大カテゴリー階層
	 * @param page     表示ページ
	 * @return ページに紐づく商品情報
	 */
	public Optional<List<Item>> findAllItem(Integer maxDepth, Integer page, String itemName, Category category,
			String brand, String orderBy) {
		maxCategoryDepth = maxDepth;
		if (orderBy.equals("")) {
			orderBy = "item.id";
		}
		
		StringBuilder sql = new StringBuilder();
		/** 要素 開始 */
		sql.append(
				"Select item.id, item.store_id, item.name, item.condition, item.category, item.brand, item.price, item.shipping, item.description, item.item_image, item.insert_date, item.insert_user,cate.hierarchy AS hierarchy");

		/** 階層に合わせて、カテゴリーの個数を変更する */
		for (int i = 0; i <= maxCategoryDepth; i++) {
			sql.append(",category" + i + ".category_name AS category_name_" + i + ", category" + i
					+ ".id AS category_id_" + i);
		}
		/** 要素 終了 */

		/** FROM 開始 */
		sql.append(" FROM items AS item LEFT JOIN categorys AS category0 ON item.category=category0.id");

		/** 階層に合わせて、カテゴリーテーブルのjoin数を変更 */
		for (int i = 0; i < maxCategoryDepth; i++) {
			sql.append(" LEFT JOIN categorytree AS categorytree" + i + " ON category" + i + ".id = categorytree" + i
					+ ".child_id LEFT JOIN categorys AS category" + (i + 1) + " ON categorytree" + i
					+ ".parent_id=category" + (i + 1) + ".id ");
		}
		sql.append("LEFT JOIN categorys AS cate ON item.category=cate.id");
		/** FROM 終了 */
		/** WHERE文 開始 */
		sql.append(" WHERE ((cate.hierarchy = 0 AND category0.id <> category1.id)");
		for(int j=1;j<=maxDepth-1;j++) {
			sql.append(" OR (");
		for(int i=0;i<j;i++ ){
		if(i!=0) {sql.append(" AND ");}
		sql.append("  (cate.hierarchy = "+j +" AND category"+i+".id <> category"+(i+1)+".id)");}
		sql.append(")");
		}
		sql.append(")");
		if(category.getHierarchy()!=null) {
			for(int i =0;i<=maxCategoryDepth;i++) {
				if(i==0) {sql.append(" AND (");}else {sql.append(" OR");}
			sql.append(" category"+i+".id = :categoryId");}
			sql.append(")");
		}
		if (!(brand.equals(""))) {
			sql.append(" AND item.brand ILIKE :brand");
		}
		if (!(itemName.equals(""))) {
			sql.append(" AND item.name ILIKE :itemName ");
		}
		;
		
		/** WHERE文 終了 */
		/** ORDER BY 開始 */
		sql.append(" ORDER BY " + orderBy + " LIMIT 100 OFFSET :page ;");
		/** ORDER BY 終了 */

		/** 入れる要素 開始 */
		Map<String, Object> paramMap = new HashMap<>();
		if (!(itemName.equals(""))) {
			paramMap.put("itemName", "%" + itemName + "%");
		}
		
		if(!(category.getId()==null)) {
			paramMap.put("categoryId",category.getId());
		}
		
		if (!(brand.equals(""))) {
			paramMap.put("brand", "%" + brand + "%");
		}
		paramMap.put("page", 1 + (page - 1) * 100);

		/** 入れる要素 終了 */
		Optional<List<Item>> itemList = template.query(sql.toString(), paramMap, ALLITEM_RESULT_SET_EXTRACTOR);
		return itemList;
	}

	/**
	 * 総商品数検索
	 * 
	 * @return 総商品数
	 */
	public Integer countTotalItem(Integer maxDepth, String itemName, Category category, String brand) {
		
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT COUNT(item.id) AS count ");
		sql.append(" FROM items AS item LEFT JOIN categorys AS category0 ON item.category=category0.id");
		/** 階層に合わせて、カテゴリーテーブルのjoin数を変更 */
		for (int i = 0; i < maxDepth; i++) {
			sql.append(" LEFT JOIN categorytree AS categorytree" + i + " ON category" + i + ".id = categorytree" + i
					+ ".child_id LEFT JOIN categorys AS category" + (i + 1) + " ON categorytree" + i
					+ ".parent_id=category" + (i + 1) + ".id ");
		}
		sql.append(" LEFT JOIN categorys AS cate ON item.category=cate.id");
		sql.append(" WHERE ((cate.hierarchy = 0 AND category0.id <> category1.id)");
		for(int j=1;j<=maxDepth-1;j++) {
			sql.append(" OR (");
		for(int i=0;i<j;i++ ){
		if(i!=0) {sql.append(" AND ");}
		sql.append("  (cate.hierarchy = "+j +" AND category"+i+".id <> category"+(i+1)+".id)");}
		sql.append(")");
		}
		sql.append(")");
		if(category.getId()!=0) {
			for(int i =0;i<=maxCategoryDepth;i++) {
				if(i==0) {sql.append(" AND (");}else {sql.append(" OR");}
			sql.append(" category"+i+".id = :categoryId");}
			sql.append(")");
		}
		if (!(brand.equals("") )) {
			sql.append(" AND item.brand ILIKE :brand");
		}
		if (!(itemName.equals(""))) {
			sql.append(" AND item.name ILIKE :itemName ");
		}
		;
		sql.append(";");
		
		Map<String, Object> paramMap = new HashMap<>();
		if (!(itemName.equals(""))) {
			paramMap.put("itemName", "%" + itemName + "%");
		}
		
		if(!(category.getId()==null)) {
			paramMap.put("categoryId",category.getId());
		}
		
		if (!(brand.equals(""))) {
			paramMap.put("brand", "%" + brand + "%");
		}

		List<Integer> itemCount = template.query(sql.toString(), paramMap, COUNT_ROW_MAPPER);
		return itemCount.get(0);
	}

	/**
	 * 商品主キー検索
	 * 
	 * @param id 商品id
	 * @return 商品情報
	 */
	public Item load(Integer id) {
		String sql = "Select item.id, item.store_id, item.name, item.condition, item.category, item.brand, item.price, item.shipping, item.description, item.item_image, item.insert_date, item.insert_user,cate.hierarchy AS hierarchy,category0.category_name AS category_name_0, category0.id AS category_id_0,category1.category_name AS category_name_1, category1.id AS category_id_1,category2.category_name AS category_name_2, category2.id AS category_id_2,category3.category_name AS category_name_3, category3.id AS category_id_3,category4.category_name AS category_name_4, category4.id AS category_id_4 FROM items AS item LEFT JOIN categorys AS category0 ON item.category=category0.id LEFT JOIN categorytree AS categorytree0 ON category0.id = categorytree0.child_id LEFT join categorys AS category1 ON categorytree0.parent_id=category1.id LEFT JOIN categorytree AS categorytree1 ON category1.id = categorytree1.child_id left join categorys AS category2 ON categorytree1.parent_id=category2.id LEFT JOIN categorytree AS categorytree2 ON category2.id = categorytree2.child_id LEFT join categorys AS category3 ON categorytree2.parent_id=category3.id LEFT JOIN categorytree AS categorytree3 ON category3.id = categorytree3.child_id  LEFT join categorys AS category4 ON categorytree3.parent_id=category4.id LEFT JOIN categorytree AS categorytree4 ON category4.id = categorytree4.child_id\r\n"
				+ "		 LEFT JOIN categorys AS cate ON item.category=cate.id  WHERE  categorytree0.depth=1 AND categorytree0.depth=1 AND categorytree1.depth=1 AND categorytree1.depth=1 AND item.id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Optional<List<Item>> itemList = template.query(sql, param, ALLITEM_RESULT_SET_EXTRACTOR);
		return itemList.get().get(0);
	}

	/**
	 * 商品情報更新
	 * 
	 * @param item 更新する商品情報
	 */
	public void update(Item item) {
		String sql = "UPDATE items SET store_id=:storeId,name=:name,condition=:condition,category=:category,brand=:brand,price=:price,shipping=:shipping,description=:description,item_image=:itemImage,insert_date=:insertDate,insert_user=:insertUser WHERE id=:id;";
		SqlParameterSource param = new BeanPropertySqlParameterSource(item);
		template.update(sql, param);
	}
}