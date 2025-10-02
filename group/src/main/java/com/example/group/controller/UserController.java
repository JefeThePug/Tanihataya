package com.example.group.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.group.entity.Users;
import com.example.group.form.RegisterUserGroup;
import com.example.group.form.UserForm;
import com.example.group.service.UserService;
import com.example.group.service.security.UserDetailsImpl;
import com.example.group.service.security.UserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserDetailsServiceImpl userDetailsService;

	//ログイン画面を表示	
	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("pageTitle", "ログイン");
		return "user/login";
	}

	// SecurityConfigのfailureUrlで指定したURLと?のうしろのパラメータ
	@GetMapping(value = "/login", params = "failure")
	public String loginFail(Model model) {
		model.addAttribute("pageTitle", "ログイン");
		model.addAttribute("failureMessage", "ログインに失敗しました");
		// ログイン画面を表示
		return "user/login";
	}

	// SecurityConfigのdefaultSuccessUrlで指定したURL
	@GetMapping("loginsuccess")
	public String loginSuccess(Model model) {
		// ユーザー名
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl principal = (UserDetailsImpl) auth.getPrincipal();
		Users user = userService.findByEmail(principal.getUsername());//Detailsを通れたメアドを元にユーザーを取得
		model.addAttribute("pageTitle", "タニハタ屋");
		model.addAttribute("user", user);//ユーザー情報を格納
		return "index";
	}

	// 新規登録画面（isEdit = false）
	@GetMapping("/register")
	public String showRegister(Model model) {
		model.addAttribute("isEdit", false);
		model.addAttribute("pageTitle", "登録");
		model.addAttribute("userForm", new UserForm());
		return "user/register";
	}

	//新規登録をする
	@PostMapping("/register")
	public String register(@Validated(RegisterUserGroup.class) @ModelAttribute UserForm userForm,
			BindingResult result, Model model, HttpServletRequest request) {
		if (result.hasErrors()) {
			model.addAttribute("Message", "入力誤り");
			return "user/register";
		}
		userService.insert(userForm);

		// 自動ログイン
		UserDetails userDetails = userDetailsService.loadUserByUsername(userForm.getEmail());
		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null,
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authToken);
		request.getSession(true).setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
		return "redirect:/user/loginsuccess";
	}

	//ユーザー情報一覧
	@GetMapping("/info/{userid}")
	public String showUserInfo(@PathVariable int userid, Model model) {
		model.addAttribute("pageTitle", "ユーザー情報");
		model.addAttribute("user", userService.findById(userid));
		return "user/user";
	}

	// ユーザー情報の変更画面
	@GetMapping("/update/{userid}")
	public String showUserUpdate(@PathVariable int userid, Model model) {
		model.addAttribute("isEdit", true);
		model.addAttribute("pageTitle", "ユーザー情報");
		model.addAttribute("user", userService.findById(userid));
		return "user/register";
	}

	//ユーザー情報の変更
	@PostMapping("/update")
	public String userUpdate(@Valid @ModelAttribute UserForm userForm) {
		userService.update(userForm);
		return "redirect:/user/info/" + userForm.getUserId();
		//ユーザー情報一覧に戻る？
	}

	//ユーザー情報の削除
	@GetMapping("/delete")
	public String showUserDelete(@PathVariable int userid) {
		userService.delete(userid);
		return "user/user_update";
	}

}
