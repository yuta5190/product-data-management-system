package com.example.batch.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;


@Repository
public class categoryBatchRepository {
@Autowired
private NamedParameterJdbcTemplate template;


private final RowMapper<String> CATEFORYNAMES_ROW_MAPPER = (rs, i) -> {
	String categoryNames=rs.getString("category_name");
	return categoryNames;
};

private final List<Map<String, Object>> categoryName(List<String> categoryNames){
	List<Map<String, Object>> categoryData = new ArrayList<>();
	Map<String, Integer> nameCount =new HashMap<>();
	String lastName=null;
	int count=1;
	
	
	for(String categoryName:categoryNames) {
	String[] fields = categoryName.split("/");
	Map<String, Object> category = new HashMap<>();
	if(fields[0].equals(lastName)) {}else {
	lastName=fields[0];
	category.put("categoryName", fields[0]);
	category.put("hierrarchy", 1);
	categoryData.add(category);}
	nameCount.put(fields[0],count);
	++count;
	}
	
	for(String categoryName:categoryNames) {
		String[] fields = categoryName.split("/");
		Map<String, Object> category = new HashMap<>();
		if(fields[1].equals(lastName)) {}else {
			lastName=fields[1];
			Integer id =nameCount.get(fields[0]);
			category.put("parent_id",id);
			category.put("categoryName", fields[1]);
			category.put("hierrarchy", 2);
			categoryData.add(category);}
			nameCount.put(fields[1],count);
			++count;
		}
	for(String categoryName:categoryNames) {
		String[] fields = categoryName.split("/");
		Map<String, Object> category = new HashMap<>();
		if(fields[2].equals(lastName)) {}else {
			lastName=fields[2];
			Integer parentId =nameCount.get(fields[0]);
			Integer childId = nameCount.get(fields[1]);
			category.put("parent_id",parentId);
			category.put("child_id",childId);
			category.put("categoryName", fields[2]);
			category.put("hierrarchy", 3);
			categoryData.add(category);}
		}return categoryData;
	}


	 public List<String> findAllCategoryName()  {	    	
	    	String sql = "SELECT category_name FROM originals";
	    	
	    	return template.query(sql,CATEFORYNAMES_ROW_MAPPER); 
	    }
	 
	 public void insertCategory(List<String> categoryNames) {
		 String insertsql = "Insert items (parent_id,child_id,category_name,hierarchy) Values (:parentId,:childId,:categoryName,:hierarchy)";
		 int[] updateCounts = template.batchUpdate(insertsql, SqlParameterSourceUtils.createBatch(categoryName(categoryNames)));
		 System.out.println(updateCounts);
	 }

}
