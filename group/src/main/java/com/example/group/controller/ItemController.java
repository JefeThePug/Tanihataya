package com.example.group.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.group.entity.Items;
import com.example.group.entity.Users;
import com.example.group.form.ItemForm;
import com.example.group.service.ItemService;
import com.example.group.service.UserService;
import com.example.group.service.security.UserDetailsImpl;

@Controller
@RequestMapping("/item")
public class ItemController {

	/**
	 * 大体30行目まで変更しました(一応変更箇所にメモ作成しました)
	 */

	private final ItemService itemService; //finalに変更
	private final UserService userService; //finalに変更

	// コンストラクタインジェクション（Lombokの@RequiredArgsConstructorでも可）
	public ItemController(ItemService itemService, UserService userService) {
		this.itemService = itemService;
		this.userService = userService;
	}

	/**
	 * ここまで変更しました
	 * @author　田辺
	 */

	// 詳細画面
	@GetMapping("/{itemId}")
	public String showItemDetail(@PathVariable int itemId, Model model) {
		//アイテムIDでアイテムを1件取得
		model.addAttribute("items", itemService.findById(itemId));
		return "item";
	}

	/*
	 * @author 田辺
	 * showPurchaseSuccessメソッドを大幅に変更しました　9/29(月曜日)
	 */

	// 購入画面表示（例： GET /item/purchase?itemId=1 ）
	@GetMapping("/purchase")
	public String showPurchase(@RequestParam int itemId, Model model) {
		Items item = itemService.findById(itemId);
		Users seller = userService.findById(item.getUserId());

		// Items.imagePaths が String[] の場合
		String[] images = item.getImagePaths().split(","); // ← 型を合わせることが重要

		model.addAttribute("item", item);
		model.addAttribute("seller", seller);
		model.addAttribute("images", images);

		return "item/purchase"; // 表示したいテンプレートに合わせる
	}

	// 購入処理情報を送信
	@PostMapping("/purchase")
	public String purchase(@RequestParam int itemId, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Users user = userService.findByEmail(userDetails.getUsername());
		model.addAttribute("user", user);
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
	public String showAddItem(@RequestParam String type, @RequestParam Integer itemId, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl principal) {
			Users user = userService.findByEmail(principal.getUsername());
			model.addAttribute("user", user);
		}
		if (type.equals("insert")) {
			model.addAttribute("itemForm", new ItemForm());
		} else if (type.equals("update")) {
			model.addAttribute("itemForm", itemService.findById(itemId));
		}

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
