package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ログイン処理
 * @author yoshida_yuuta
 *
 */
@Controller
@RequestMapping("/login-user")
public class LoginController {

	/**
	 * ログイン画面表示
	 * @param model　モデル
	 * @param error　エラー
	 * @return　ログイン表示画面
	 */
	@GetMapping("")
	public String login(Model model, @RequestParam(required = false) String error) {
		if (error != null) {
			model.addAttribute("errorMessage", "Invalid email address or password.");
		}
		return "login";

	}}
