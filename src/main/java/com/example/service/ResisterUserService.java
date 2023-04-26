package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.repository.UserRepository;

/**
 * ユーザー登録のサービス
 * 
 * @author yoshida_yuuta
 *
 */
@Service
@Transactional
public class ResisterUserService {
	@Autowired
	private UserRepository userRepository;

	public void insertUser(User user) {
		userRepository.inserUser(user);

	}

	public User findByMailAddress(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}
}