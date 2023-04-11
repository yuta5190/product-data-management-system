package com.example.batch;

import java.sql.*;
import java.util.*;

import com.example.domain.Category;
import com.example.domain.CategoryTree;

public class categoryBatch {
	public static void main(String[] args) {

		try {
			// データベースに接続するための情報を設定する
			String url = "jdbc:postgresql://localhost:5432/data_processing";
			String user = "postgres";
			String password = "postgres";

			// originalsDBよりカテゴリー名のリストを受け取る
			List<String> data = findAllName(url, user, password);

			// データをバッチ処理してデータベースに挿入する
			insertData(data, url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 全件検索
	public static List<String> findAllName(String url, String user, String password) throws SQLException {
		ResultSet rs = null;
		Connection con = DriverManager.getConnection(url, user, password);
		String sql = "SELECT category_name FROM originals GROUP BY category_name ORDER BY category_name";
		PreparedStatement pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		List<String> nameList = new ArrayList<>();
		while (rs.next()) {
			String categoryName = rs.getString("category_name");
			nameList.add(categoryName);
		}
		return nameList;
	}

	public static void insertData(List<String> data, String url, String user, String password) throws SQLException {

		int count = 1;
		Map<String, Category> categoryMap = new HashMap<>();
		List<String> categoryKeyList = new ArrayList<>();
		for (String categoryName : data) {
			if (categoryName.contains("/")) {
				String[] fields = categoryName.toString().split("/");
				for (int i = 0; i < fields.length; i++) {
					Category category = new Category();
					StringBuilder key = new StringBuilder();
					for (int j = 0; j <=i; j++) {
						key.append("/" + fields[j]);
					}key.delete(0, 1);
					if (categoryMap.get(key.toString()) == null) {
						category.setId(count);
						count++;
						category.setCategoryName(fields[i]);
						category.setHierarchy(i);
						categoryKeyList.add(key.toString());
						categoryMap.put(key.toString(), category);
					}
				}
			}
		}
		System.out.println(1);
		Connection conn = DriverManager.getConnection(url, user, password);
		PreparedStatement pstmt = conn
				.prepareStatement("Insert INTO categorys (category_name,hierarchy,category_root) Values (?,?,?) ;");
		int counts = 0;
		for (String key : categoryKeyList) {
			Category category = categoryMap.get(key);
		
			pstmt.setString(1, category.getCategoryName());
			pstmt.setInt(2, category.getHierarchy());
			pstmt.setString(3, key);
			pstmt.addBatch();
			if (++counts % 10 == 0) {
				System.out.println(counts);
				pstmt.executeBatch();
			}

		}
		pstmt.executeBatch();
		System.out.print("完了");
		pstmt.close();
		
		
		System.out.println(2);
		List<CategoryTree> categoryTreeList = new ArrayList<>();
		for(String categorykey:categoryKeyList) {
			String[] categoryName;
			if (categorykey.contains("/")) {
			categoryName=categorykey.toString().split("/");}else {
			categoryName=new String[0];
			categoryName[0]=categorykey;
			}
			for(int i = 0;i<categoryName.length;i++) {
				CategoryTree tree =new CategoryTree();
				StringBuilder key = new StringBuilder();
				for(int j=0;j<=i;i++) {
					key.append("/"+categoryName[j]);
				}key.delete(0, 1);
				tree.setParentId(categoryMap.get(key.toString()).getId());
				tree.setChildId(categoryMap.get(categorykey).getId());
				tree.setDepth(categoryName.length-1-i);
				System.out.println();
				categoryTreeList.add(tree);
			}
	}
		PreparedStatement pstmttree = conn
				.prepareStatement("Insert INTO categorytree (parent_id,child_id,depth) Values (?,?,?) ;");
	    count = 0;
		for (CategoryTree category : categoryTreeList) {
			pstmttree.setInt(1, category.getParentId());
			pstmttree.setInt(2, category.getChildId());
			pstmttree.setInt(3,category.getDepth());
			pstmttree.addBatch();
			if (++count % 10 == 0) {
				System.out.println(count);
				pstmt.executeBatch();
			}}
		
		pstmt.executeBatch();
		System.out.print("完了");
		pstmt.close();
	}
	
	}		
	

