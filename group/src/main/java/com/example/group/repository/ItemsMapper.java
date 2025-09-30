
package com.example.group.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.group.entity.Items;

@Mapper
public interface ItemsMapper {

	//カテゴリーごとのアイテム一覧表示
	List<Items>findAllByCategory(Integer category);
	
	//特定ユーザーの購入履歴一覧表示
	List<Items>findPurchasesByUserId(Integer userId);
	
	//特定ユーザーのすべての販売履歴
	List<Items>findSalesByUserId(Integer userId);
	
	// アイテムIDで1件取得
	Items findById(Integer itemId);

	//アイテムを追加
	void insertItem(Items items);

	//アイテムを更新
	//引数ItemFromからItemsに変更　9/30(田辺)
	void update(Items items);

	//アイテムに削除フラグを付与
	void markForDelete(Integer item_id);
	
	/**
	 * 購入情報を更新し、販売ステータスを販売終了にします。
	 */
	void updatePurchaseInfo(
        @Param("itemId") Integer itemId, 
        @Param("buyerId") Integer buyerId, 
        @Param("purchaseDate") LocalDateTime purchaseDate
    );
	

}
