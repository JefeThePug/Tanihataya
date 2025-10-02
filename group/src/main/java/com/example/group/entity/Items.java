package com.example.group.entity;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Items {

	//出品物のID
	private Integer itemId;

	//出品者のID
	private Integer userId;

	//出品者の名前
	private String name;

	//商品カテゴリー(1~8の数字で管理)
	private String category;

	//商品詳細
	private String detail;

	//価格
	private Integer price;
	
	//販売状況フラグ
	private boolean saleStatus;

	//購入者のuserId String→Integerに変更しました 9/30(火)
	private Integer buyUser;
	
	// 画像パス配列に変更 9/29(月)
    private String[] imagePaths;
	
    //商品登録日時
	private LocalDateTime createdAt;

	/**
	 * 販売者が商品情報を更新した時間
	 */
	private LocalDateTime updatedAt;
	
	// 【追加】購入日時 9/30(火) by田辺
	private LocalDateTime purchaseDate; 
}
