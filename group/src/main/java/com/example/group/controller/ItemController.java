package com.example.group.controller;

import java.beans.PropertyEditorSupport;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.group.entity.Items;
import com.example.group.entity.Payment;
import com.example.group.entity.Users;
import com.example.group.form.ItemForm;
import com.example.group.form.PaymentForm;
import com.example.group.service.ItemService;
import com.example.group.service.PurchaseService;
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
	private final PurchaseService paymentService;
	 
	// コンストラクタインジェクション（Lombokの@RequiredArgsConstructorでも可）
	public ItemController(ItemService itemService, UserService userService,PurchaseService paymentService) {
		this.itemService = itemService;
		this.userService = userService;
		this.paymentService = paymentService;
	}

	/**
	 * ここまで変更しました
	 * @author　田辺
	 */

	// 詳細画面
	@GetMapping("/{itemId}")
	public String showItemDetail(@PathVariable int itemId, Model model, Principal principal) {
		//user情報（principal）があれば登録
		if (principal != null) {
		 // Principal からログインユーザーを取得
	    String email = principal.getName();
	    // ログイン中のユーザー情報をDBから取得
	    Users user = userService.findByEmail(email);
		    model.addAttribute("user", user);
		    }
	    
		//アイテムIDでアイテムを1件取得
		Items items = itemService.findById(itemId);
		model.addAttribute("item", items);
		return "item/item";
	}

	/*
	 * @author 田辺
	 * showPurchaseSuccessメソッドを大幅に変更しました　9/29(月曜日)
	 */

	// 購入画面表示（例： GET /item/purchase?itemId=1 ）
	@GetMapping("/purchase")
	public String showPurchase(@RequestParam int itemId, Model model, Principal principal) {
	    String email = principal.getName();
	    Users user = userService.findByEmail(email);
		model.addAttribute("user", user);//購入ユーザー情報登録
	    
		Items item = itemService.findById(itemId);//itemの情報
		Users seller = userService.findById(item.getUsersId());//出品者の情報
		Payment payment = new Payment();

		// Items.imagePaths が String[] の場合
//		String[] images = item.getImagePaths().split(","); // ← 型を合わせることが重要
//		model.addAttribute("images", images);

		model.addAttribute("item", item);
		model.addAttribute("seller", seller);
		model.addAttribute("purchaseForm",payment);
		

		return "item/purchase";// 表示したいテンプレートに合わせる
	}
	
	//クレカ情報のハイフン等を削除
	@InitBinder("purchaseForm")
	public void initBinder(WebDataBinder binder) {
	    binder.registerCustomEditor(String.class, "cardNumber", new PropertyEditorSupport() {
	        @Override
	        public void setAsText(String text) {
	            if (text != null) {
	                // スペースとハイフンを除去
	                setValue(text.replaceAll("[\\s-]", ""));
	            } else {
	                setValue(null);
	            }
	        }
	    });
	    
	}
	
	// 購入処理情報を送信
	@PostMapping("/purchase")
	public String purchase(
		    @RequestParam int itemId,
		    @Valid @ModelAttribute("purchaseForm") PaymentForm purchaseForm,
		    BindingResult bindingResult,
		    Model model,
		    Principal principal,
		    RedirectAttributes redirectAttributes) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Users user = userService.findByEmail(userDetails.getUsername());
		 redirectAttributes.addFlashAttribute("user", user);

		Items item = itemService.findById(itemId);//itemの情報
		itemService.completePurchase(itemId, user.getUsersId());
		 redirectAttributes.addFlashAttribute("itemid", itemId);
		 
		  if (purchaseForm.isSaveCardInfo()) {
		        paymentService.insertPayment(purchaseForm);
		    }
		  
		return "redirect:/item/purchase/success";
		//purchase/successのURLへアクセス
	}

	// 購入完了画面
	@GetMapping("/purchase/success")
	public String showPurchaseSuccess(@ModelAttribute("itemid") int itemId, Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		Users user = userService.findByEmail(userDetails.getUsername());
		model.addAttribute("user", user);

		Items item = itemService.findById(itemId);//itemの情報
		Users seller = userService.findById(item.getUsersId());//出品者の情報

		model.addAttribute("item", item);
		model.addAttribute("seller", seller);

		return "item/success";
	}


	// 出品登録/変更画面表示
	@GetMapping("/add_item")
	public String showAddItem(@RequestParam String type, @RequestParam Integer itemId, Model model) {
		// メニューバー用に認証済みユーザーを取得する
		Users user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl principal) {
			user = userService.findByEmail(principal.getUsername());
			// ユーザーが null でなければ、モデルに追加する
			model.addAttribute("user", user);
		}
		// type が insert の場合、空のフォームで add_item HTML ページを表示する
		if (type.equals("insert")) {
			ItemForm form = new ItemForm();
			form.setUserId(user.getUsersId());
			model.addAttribute("itemForm", form);
		} 
		// type が update の場合、
		else if (type.equals("update")) {
			// アイテムIDに基づいて、完全なアイテム情報を取得する
			Items item = itemService.findById(itemId);
			// アイテムの usersId が認証済みユーザーの usersId と一致しない場合、
			// アクセスを拒否してエラーページにリダイレクトする
			if (item == null || !item.getUsersId().equals(user.getUsersId())) {
				throw new org.springframework.security.access.AccessDeniedException("この商品を更新する権限がありません。");
			}
			// エンティティからフォームへの変換メソッドに渡すために、
			// （1つのアイテムを含む）新しいアイテムのリストを作成する
			List<Items> items = new ArrayList<>();
			items.add(item);
			// そのリストから返された単一のフォームを取得し、モデルに渡す
			ItemForm form = itemService.entitiesToForm(items).get(0);
			model.addAttribute("itemForm", form);
		}
		// POST リクエスト用に itemId と type をモデルに送る
		model.addAttribute("itemId", itemId);
		model.addAttribute("type", type);

		return "item/add_item"; // add_item.htmlを表示
	}

	// 出品処理  	
	@PostMapping("/add_item")
	public String addItem(@RequestParam String type, @ModelAttribute ItemForm itemForm) {
		// type に基づいて、itemForm の内容を使って更新（UPDATE）または挿入（INSERT）を行う
		if ("insert".equals(type)) {//新規登録
			itemService.insert(itemForm);
		} else if ("update".equals(type)) {//変更登録
			itemService.update(itemForm);
		}
		return "redirect:/list?type=sell&userId=" + itemForm.getUserId();
		//出品一覧へ移行　（出品画面へ移動の方がいい？）
	}
}
