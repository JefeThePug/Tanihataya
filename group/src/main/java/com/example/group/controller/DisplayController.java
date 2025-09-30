package com.example.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.group.service.ItemService;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class DisplayController {
	
	private final ItemService itemService;

    @GetMapping
    public String show(Model model) {
        // トップ画面：カテゴリ一覧表示
    	model.addAttribute("pageTitle", "タニハタ屋");
        return "index";
    }
    // カテゴリ一覧表示
    @GetMapping("/{category}")
    public String showCategories(@PathVariable int category, Model model) {
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
    public String showList(@RequestParam String type, @RequestParam int userId, Model model) {
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

