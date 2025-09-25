package com.example.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/item")
public class ItemController {
	
	private ItemService itemService;

	// 商品詳細画面
    @GetMapping("/{itemId}")
    public String showItemDetail(@PathVariable int itemId, Model model) {
    	//アイテムIDでアイテムを1件取得
    	model.addAttribute("items",itemService.findById(itemId));
        return "item";
    }

    @GetMapping("/purchase")
    public String showPurchaseScreen(@RequestParam int itemId, Model model) {
        // 購入画面表示
        return "item/purchase";
    } 

    @PostMapping("/purchase")
    public String purchase(@RequestParam int itemId, Model model) {
        // 購入処理情報を送信
        return "redirect:/item/purchase/success"; 
        //purchase/successのURLへアクセス
    }

    @GetMapping("/purchase/success")
    public String showPurchaseSuccess() {
        // 購入完了画面
        return "item/success";
    }

    @GetMapping("/add_item")
    public String showAddItem(Model model) {
        // 出品画面表示
        return "item/add_item";
    }

    @PostMapping("/add_item")
    public String addItem(@ModelAttribute ItemForm itemForm) {
        // 出品処理  	
        return "redirect:/add_item";
        //出品画面へ移動（出品一覧？/solditems）
    }
}
