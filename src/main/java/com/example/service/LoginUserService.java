package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.repository.UserRepository;

@Service
@Transactional
public class LoginUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public User login(LoginUserForm form) {
		User user = userRepository.findByEmailAndPass(form.getEmail(),form.getPassword() );
		if (!passwordEncoder.matches(form.getPassword(), user.getPassword())) {
			return null;
		}
		return user;
	}
}
