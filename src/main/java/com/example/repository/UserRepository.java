package com.example.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;

/**
 * ユーザーリポジトリ
 * 
 * @author yoshida_yuuta
 *
 */
@Mapper
@Repository
@Transactional
public interface UserRepository {

	/**
	 * ユーザー登録
	 * 
	 * @param user 登録情報
	 */
	void inserUser(User user);

	/**
	 * アドレス重複検索
	 * 
	 * @param email 登録するメールアドレス
	 * @return ユーザー情報
	 */
	User findByEmail(@Param("email") String email);

}
