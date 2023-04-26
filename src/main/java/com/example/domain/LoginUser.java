package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class LoginUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = 1L;
	/** 管理者情報 */
	private final User user;

	public LoginUser(User user, Collection<GrantedAuthority> authorityList) {

		super(user.getEmail(), user.getPassword(), authorityList);
		this.user = new User(user);
	}

	public User getUser() {
		return user;
	}

}
