package com.example.batch;

import java.sql.*;
import java.util.*;

import com.example.batch.domain.Batch;

public class categorytreeBatch {

	public static void main(String[] args) {

		try {
			// データベースに接続するための情報を設定する
			String url = "jdbc:postgresql://localhost:5432/data_processing";
			String user = "postgres";
			String password = "postgres";

			// originalsDBよりカテゴリー名のリストを受け取る
			Map<String, Batch> data = findAllName(url, user, password);

			// データをバッチ処理してデータベースに挿入する
			insertTree(data, url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全件検索
	public static Map<String, Batch> findAllName(String url, String user, String password) throws SQLException {
		ResultSet rs = null;
		Connection con = DriverManager.getConnection(url, user, password);
		String sql = "SELECT id,category_root,hierarchy FROM categorys Order BY id";
		PreparedStatement pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		Map<String, Batch> categoryMap = new HashMap<>();
		while (rs.next()) {
			Batch batch = new Batch();
			batch.setId(rs.getInt("id"));
			batch.setDepth(rs.getInt("hierarchy"));
			categoryMap.put(rs.getString("category_root"), batch);
		}
		return categoryMap;
	}

	public static void insertTree(Map<String, Batch> map, String url, String user, String password)
			throws SQLException {
		Connection con = DriverManager.getConnection(url, user, password);
		int count = 0;
		PreparedStatement pstmt = con
				.prepareStatement("Insert INTO categorytree (parent_id,child_id,depth) Values (?,?,?) ;");
		for (String categorykey : map.keySet()) {
			String[] fields = new String[1];

			List<String[]> list = new ArrayList<>();
			if (categorykey.contains("/")) {
				fields = categorykey.split("/");

				for (int i = 1; i <= map.get(categorykey).getDepth() + 1; i++) {
					StringBuilder parentCategory = new StringBuilder();
					for (int j = 0; j < i; j++) {
						parentCategory.append("/" + fields[j]);

					}
					parentCategory.delete(0, 1);
					String[] category = new String[2];
					category[0] = parentCategory.toString();
					category[1] = categorykey;
					System.out.println(category[0] + ":" + category[1]);
					list.add(category);
				}
			} else {
				String[] category = new String[2];
				category[0] = categorykey;
				category[1] = categorykey;
				System.out.println(category[0] + ":" + category[1]);
				list.add(category);
			}
			for (String[] allcategory : list) {
				System.out.println("ANS:" + allcategory[0]);
				pstmt.setInt(1, map.get(allcategory[0]).getId());
				pstmt.setInt(2, map.get(allcategory[1]).getId());
				pstmt.setInt(3, map.get(allcategory[1]).getDepth() - map.get(allcategory[0]).getDepth());
				pstmt.executeUpdate();
			}
			System.out.println(count);
			++count;
		}
	}

}
