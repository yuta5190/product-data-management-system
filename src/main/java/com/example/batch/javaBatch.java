package com.example.batch;
import java.io.*;
import java.sql.*;
import java.util.*;

public class javaBatch {
	
	    public static void main(String[] args) {
	        try {
	            // データベースに接続するための情報を設定する
	            String url = "jdbc:postgresql://localhost:5432/data_processing";
	            String user = "postgres";
	            String password = "postgres";
	         
	            // TSVファイルを読み込む
	            List<String[]> data = readData("C:\\env\\springworkspace\\product-data-management-system\\src\\main\\resources\\static\\train.tsv");

	            // データをバッチ処理してデータベースに挿入する
	            insertData(data, url, user, password);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public static List<String[]> readData(String ex) throws IOException {
	        List<String[]> result = new ArrayList<>();
	        BufferedReader reader = new BufferedReader(new FileReader(ex));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            String[] fields = line.split("	");
	            result.add(fields);
	        }
	        reader.close();
	        return result;
	    }

	    public static void insertData(List<String[]> data, String url, String user, String password) throws SQLException {
	        Connection conn = DriverManager.getConnection(url, user, password);
	        PreparedStatement pstmt = conn.prepareStatement("INSERT INTO originals (id,name,condition_id,category_name,brand,price,shipping,description) VALUES (?,?,?,?,?,?,?,?) ;");
	        int count = 0;
	        for (String[] fields : data) { 
	            pstmt.setInt(1, Integer.parseInt(fields[0]));
	            pstmt.setString(2, fields[1]);
	            pstmt.setInt(3, Integer.parseInt(fields[2]));
	            pstmt.setString(4, fields[3]);
	            pstmt.setString(5, fields[4]);
	            pstmt.setDouble(6, Double.parseDouble(fields[5]));
	            pstmt.setInt(7, Integer.parseInt(fields[6]));
	            if(fields.length==8){
	            pstmt.setString(8, fields[7]);}else{pstmt.setString(8, "");}
	            pstmt.addBatch();
	            if (++count % 1000== 0) {
	                pstmt.executeBatch();
	            }
	        }
	        pstmt.executeBatch();
	        System.out.print("完了");
	        pstmt.close();
}}
