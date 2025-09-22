package com.example.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class DisplayController {

	//DI★サービス名称確認要★
//	private final ItemService itemService;
	
	//カテゴリ表示　トップ画面
	@GetMapping
	public String show() {
		return "index";
	}

	//商品一覧（カテゴリ一覧）
	@GetMapping("/{category}")
	public String showCategory(@PathVariable String category ,Model model) {
		//Listをまとめて★名称確認要カテゴリソートしたメソッド★
//		model.addAttribute("category",itemServiceAll());
		return "list";


	}@GetMapping("/purchases")
	public String showPurchases(Model model) {
		return "list";
	}
	@GetMapping("/solditems")
	public String showSoldItems() {
		return "list";
	}

}
