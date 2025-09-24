package com.example.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.group.service.ItemService;


@Controller
@RequestMapping("/")
public class DisplayController {
	
	private ItemService itemService;

    @GetMapping
    public String show(Model model) {
        // トップ画面：カテゴリ一覧表示
        return "index";
    }
    // カテゴリ一覧表示
    @GetMapping("/{category}")
    public String showCategories(@PathVariable int category, Model model) {
        model.addAttribute("categories", itemService.findAllByCategory(category));
        return "category/list";
    }

    // 出品/購入の一覧表示
    @GetMapping("/list")
    public String showList(@RequestParam String type, @RequestParam int userId, Model model) {
        if ("buy".equals(type)) {//購入検索
            model.addAttribute("items", itemService.findPurchasesByUserId(userId));
        } else if ("sell".equals(type)) {//販売検索
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

