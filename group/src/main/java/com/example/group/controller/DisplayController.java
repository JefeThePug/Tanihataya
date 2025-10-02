package com.example.group.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.group.entity.Users;
import com.example.group.service.ItemService;
import com.example.group.service.UserService;
import com.example.group.service.security.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DisplayController {

	private final ItemService itemService;
	private final UserService userService;

	@GetMapping
	public String show(Model model, Authentication authentication) {
		// トップ画面：カテゴリ一覧表示
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl principal) {
			Users user = userService.findByEmail(principal.getUsername());
			model.addAttribute("user", user);
		}
		model.addAttribute("pageTitle", "タニハタ屋");
		return "index";
	}

	// カテゴリ一覧表示
	@GetMapping("/c/{category}")
	public String showCategories(@PathVariable int category, Model model, Authentication authentication) {
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl principal) {
			Users user = userService.findByEmail(principal.getUsername());
			model.addAttribute("user", user);
		}
		String cat = switch (category) {
		case 1 -> "衣類";
		case 2 -> "おもちゃ";
		case 3 -> "電化製品";
		case 4 -> "スポーツ";
		case 5 -> "ペット";
		case 6 -> "美容";
		case 7 -> "書籍";
		default -> "その他";
		};
		model.addAttribute("pageTitle", "タニハタ屋:" + cat);
		model.addAttribute("header", cat);
		model.addAttribute("items", itemService.findAllByCategory(category));
		return "list";
	}

	// 出品/購入の一覧表示
	@GetMapping("/list")
	public String showList(@RequestParam String type, @RequestParam int userId, Model model,
			Authentication authentication) {
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl principal) {
			Users user = userService.findByEmail(principal.getUsername());
			model.addAttribute("user", user);
		}

		if ("buy".equals(type)) {//購入検索
			model.addAttribute("pageTitle", "タニハタ屋:購入");
			model.addAttribute("header", "購入");
			model.addAttribute("items", itemService.findPurchasesByUserId(userId));
		} else if ("sell".equals(type)) {//販売検索
			model.addAttribute("pageTitle", "タニハタ屋:販売");
			model.addAttribute("header", "販売");
			model.addAttribute("items", itemService.findSalesByUserId(userId));
		}
		return "list";
		//HTMLの例        
		//        <form action="/list" method="get" style="display:inline;">
		//        <input type="hidden" name="type" value="buy" />
		//        <input type="hidden" name="userId" value="${userid}" />
		//        <button type="submit">購入一覧</button>
		//    </form>
	}

}
