package com.example.group.controller;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.group.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;

	//ログイン画面を表示	
	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}
	
	//新規登録画面を表示
	@GetMapping("/register")
	public String showRegister() {
		return "user/register";
	}
	
	//新規登録をする
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute UserForm userForm) {	
		userService.insert(userForm); 
		return "redirect:user/register";
	}
	
	//ユーザー情報一覧
	@GetMapping("/info/{userid}")
	public String showUserInfo(@PathVariable int userid , Model model) {
		model.addAttribute("user",userService.findById(userid));
		return "user/user";
	}
	
	//ユーザー情報の変更表示
    @GetMapping("/update")
	public String showUserUpdate() {
    	return "user/user_update";
	}
	
    //ユーザー情報の変更
	@PostMapping("/update")
	public String UserUpdate(@Valid @ModelAttribute UserForm userForm) {
		userService.update(userForm);
		return "redirect:/user/info/{userid}";
		//ユーザー情報一覧に戻る？
	}

}
