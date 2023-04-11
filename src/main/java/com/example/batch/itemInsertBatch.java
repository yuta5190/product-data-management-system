package com.example.batch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.domain.Item;

public class itemInsertBatch {
	public static void main(String[] args) {

		try {
			// データベースに接続するための情報を設定する
			String url = "jdbc:postgresql://localhost:5432/data_processing";
			String user = "postgres";
			String password = "postgres";

			// originalsDBよりカテゴリー名のリストを受け取る
			Map<String, Integer> categoryListMap = findCategoryName(url, user, password);
			System.out.println("1");

			// originalsDBよりアイテムのリストを受け取る
			List<Item> categoryList = findAllItem(url, user, password, categoryListMap);
			System.out.println("2");
			// データをバッチ処理してデータベースに挿入する
			insertData(categoryList, url, user, password);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// カテゴリー名を探す
	public static Map<String, Integer> findCategoryName(String url, String user, String password) throws SQLException {
		ResultSet rs = null;
		Connection con = DriverManager.getConnection(url, user, password);
		String sql = "SELECT id,category_root FROM categorys WHERE hierarchy >= 2";
		PreparedStatement pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		Map<String, Integer> category = new HashMap<String, Integer>();
		while (rs.next()) {
			category.put(rs.getString("category_root"), rs.getInt("id"));
		}
		return category;
	}

	// 全件検索
	public static List<Item> findAllItem(String url, String user, String password, Map<String, Integer> categoryMap)
			throws SQLException {
		ResultSet rs = null;
		Connection con = DriverManager.getConnection(url, user, password);
		String sql = "SELECT id,name,condition_id,brand,price,shipping,description,category_name FROM originals WHERE id>=750000 ORDER BY id";
		PreparedStatement pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<Item> itemList = new ArrayList<>();

		while (rs.next()) {
			Item item = new Item();
			item.setId(rs.getInt("id"));
			item.setName(rs.getString("name"));
			item.setCondition(rs.getInt("condition_id"));
			item.setBrand(rs.getString("brand"));
			item.setPrice(rs.getDouble("price"));
			item.setShipping(rs.getInt("shipping"));
			item.setDescription(rs.getString("description"));
			if (rs.getString("category_name") == null) {
				item.setCategory(null);
			} else {
				item.setCategory(categoryMap.get(rs.getString("category_name")));
			}
			item.setDescription(rs.getString("description"));
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			item.setInsertDate(timestamp);
			item.setInsertUser("batch");
			item.setItemImage("image_" + rs.getInt("id"));
			itemList.add(item);
		}
		return itemList;
	}

	public static void insertData(List<Item> data, String url, String user, String password) throws SQLException {
		Connection conn = DriverManager.getConnection(url, user, password);
		PreparedStatement pstmt = conn.prepareStatement(
				"Insert INTO items (id,name,condition,category,brand,price,shipping,description,insert_date,insert_user) Values (?,?,?,?,?,?,?,?,?,?) ;");
		int count = 0;
		for (Item item : data) {
			pstmt.setInt(1, item.getId());
			pstmt.setString(2, item.getName());
			pstmt.setInt(3, item.getCondition());
			if(item.getCategory()==null) {}else {
			pstmt.setInt(4, item.getCategory());}
			pstmt.setString(5, item.getBrand());
			pstmt.setDouble(6, item.getPrice());
			pstmt.setInt(7, item.getShipping());
			pstmt.setString(8, item.getDescription());
			pstmt.setTimestamp(9, item.getInsertDate());
			pstmt.setString(10, item.getInsertUser());
			pstmt.addBatch();
			if (++count % 1000 == 0) {
				System.out.println(count);
				pstmt.executeBatch();
			}
		}
		pstmt.executeBatch();
		System.out.print("完了");
		pstmt.close();
	}
}
