package com.example.group.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	//ログイン画面を表示
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
	public String register(@ModelAttribute UserForm UserForm) {		
		return "redirect:user/register";
	}
	
	//ユーザー情報一覧
	@GetMapping("/info")
	public String showUserInfo(@PathVariable List<user> user, Model model) {
		return "user/user";
	}
	
	//ユーザー情報の変更表示
    @GetMapping("/update")
	public String showUserUpdate() {
    	return "user/user_update";
	}
	
    //ユーザー情報の変更
	@PostMapping("/update")
	public String UserUpdate(@ModelAttribute UserForm userForm) {
		return "redirect:/user/update";

	}

}
