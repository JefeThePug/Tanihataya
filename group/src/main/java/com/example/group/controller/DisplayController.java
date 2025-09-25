package com.example.group.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class DisplayController {

    @GetMapping
    public String show(Model model) {
        // トップ画面：カテゴリ一覧表示
        return "index";
    }

    @GetMapping("/{category}")
    public String showCategory(@PathVariable String category, Model model) {
        // 指定カテゴリの商品一覧表示
        return "list";
    }

    @GetMapping("/purchases")
    public String showPurchases(Model model) {
        // 購入済み商品の一覧表示
        return "list";
    }

    @GetMapping("/solditems")
    public String showSoldItems(Model model) {
        // 出品済み商品の一覧表示
        return "list";
    }
}

