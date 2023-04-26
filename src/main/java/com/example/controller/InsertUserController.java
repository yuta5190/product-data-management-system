package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.domain.User;
import com.example.form.InsertUserForm;
import com.example.service.ResisterUserService;

/**
 * ユーザー登録コントローラー
 * @author yoshida_yuuta
 *
 */
@Controller
@RequestMapping("/insert-user")
public class InsertUserController {
	@Autowired
	private ResisterUserService resisterUserService;
	/**
	 * 
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * ユーザー登録画面表示
	 * @param form　登録情報
	 * @param model　モデル
	 * @return　登録画面
	 */
	@GetMapping("")
	public String index(InsertUserForm form, Model model) {
		return "register";
	}

	/**
	 * ユーザー登録
	 * @param form　ユーザー登録情報
	 * @param result　エラー情報
	 * @param redirectAttributes　リダイレクト時入力情報引継ぎ
	 * @param model　モデル
	 * @return　商品一覧画面
	 */
	@PostMapping("/insert")
	public String insert(@Validated InsertUserForm form, BindingResult result, RedirectAttributes redirectAttributes,
			Model model) {
		/** パスワードと確認用パスワードの入力値チェックをします. */
		if (!(form.getPassword().equals(form.getConfirmationPassword()))) {
			FieldError fieldError = new FieldError(result.getObjectName(), "confirmationPassword",
					"Password and confirmation password do not match.");
			result.addError(fieldError);
		}

		/** メールアドレスの重複チェックをします. */
		User userMailAddress = resisterUserService.findByMailAddress(form.getEmail());
		if (!(userMailAddress == null)) {
			FieldError fieldError = new FieldError(result.getObjectName(), "email", "That email address is already in use.");
			result.addError(fieldError);
		}

		/** エラーが発生したら登録画面にリターンする. */
		if (result.hasErrors()) {
			return index(form, model);
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setPassword(passwordEncoder.encode(form.getPassword()));
		resisterUserService.insertUser(user);
		return "redirect:/login-user";
	}
}
