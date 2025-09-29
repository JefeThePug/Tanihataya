package com.example.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.group.form.ItemForm;
import com.example.group.service.ItemService;


@Controller
@RequestMapping("/item")
public class ItemController {
	
	private ItemService itemService;

	// 詳細画面
    @GetMapping("/{itemId}")
    public String showItemDetail(@PathVariable int itemId, Model model) {
    	//アイテムIDでアイテムを1件取得
    	model.addAttribute("items",itemService.findById(itemId));
        return "item";
    }

    // 購入画面表示
    @GetMapping("/purchase")
    public String showPurchaseScreen(@RequestParam int itemId, Model model) {
    	model.addAttribute("items",itemService.findById(itemId));
        return "item/purchase";
    } 

    // 購入処理情報を送信
    @PostMapping("/purchase")
    public String purchase(@RequestParam int itemId, Model model) {
    	//★PurchaseService購入した情報送信のメソッド
        return "redirect:/item/purchase/success"; 
        //purchase/successのURLへアクセス
    }

    // 購入完了画面
    @GetMapping("/purchase/success")
    public String showPurchaseSuccess() {
        return "item/success";
    }

    // 出品登録/変更画面表示
    @GetMapping("/add_item")
    public String showAddItem(Model model) {

        
        return "item/add_item";
    }


    // 出品処理  	
    @PostMapping("/add_item")
    public String addItem(@RequestParam String type, @ModelAttribute ItemForm itemForm) {
        if ("insert".equals(type)) {//新規登録
        	itemService.insert(itemForm);
        } else if ("update".equals(type)) {//変更登録
            itemService.update(itemForm);
        }
    	return "redirect:/list?type=sell&userId=" + itemForm.getUserId();
        //出品一覧へ移行　（出品画面へ移動の方がいい？）
    }
}
