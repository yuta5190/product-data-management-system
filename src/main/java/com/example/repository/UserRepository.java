package com.example.repository;

import java.util.List;

import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

@Repository
public class UserRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		try {
		user.setStoreId(rs.getInt("store_id"));}catch(PSQLException e) {};
		user.setAuthority(rs.getInt("authority"));
		
		return user;
	};

	public void InsertUser(User user) {
		System.out.println(user);
		String sql = "Insert INTO users (name,email,password,authority,zipcode,address,telephone,store_id,enabled) VALUES(:name,:email,:password,:authority,:zipcode,:address,:telephone,:storeId,:enabled)";
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql, param);
	}

	public User findByMailAddress(String email) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone,authority,store_id,enabled FROM users WHERE email=:email;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	public User findByEmailAndPass(String email, String password) {
		String sql = "SELECT id,name,email,password,zipcode,address,telephone,authority,store_id,enabled FROM users WHERE email=:email AND password=:password;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}
}
